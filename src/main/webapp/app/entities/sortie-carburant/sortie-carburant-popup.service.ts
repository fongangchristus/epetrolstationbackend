import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { DatePipe } from '@angular/common';
import { SortieCarburant } from './sortie-carburant.model';
import { SortieCarburantService } from './sortie-carburant.service';

@Injectable()
export class SortieCarburantPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private sortieCarburantService: SortieCarburantService

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
                this.sortieCarburantService.find(id)
                    .subscribe((sortieCarburantResponse: HttpResponse<SortieCarburant>) => {
                        const sortieCarburant: SortieCarburant = sortieCarburantResponse.body;
                        sortieCarburant.date = this.datePipe
                            .transform(sortieCarburant.date, 'yyyy-MM-ddTHH:mm:ss');
                        this.ngbModalRef = this.sortieCarburantModalRef(component, sortieCarburant);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.sortieCarburantModalRef(component, new SortieCarburant());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    sortieCarburantModalRef(component: Component, sortieCarburant: SortieCarburant): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.sortieCarburant = sortieCarburant;
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
