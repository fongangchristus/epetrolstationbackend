import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { TypeTresorerie } from './type-tresorerie.model';
import { TypeTresorerieService } from './type-tresorerie.service';

@Injectable()
export class TypeTresoreriePopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private typeTresorerieService: TypeTresorerieService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.typeTresorerieService.find(id)
                    .subscribe((typeTresorerieResponse: HttpResponse<TypeTresorerie>) => {
                        const typeTresorerie: TypeTresorerie = typeTresorerieResponse.body;
                        this.ngbModalRef = this.typeTresorerieModalRef(component, typeTresorerie);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.typeTresorerieModalRef(component, new TypeTresorerie());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    typeTresorerieModalRef(component: Component, typeTresorerie: TypeTresorerie): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.typeTresorerie = typeTresorerie;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}
