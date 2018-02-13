import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { TypeIntervenant } from './type-intervenant.model';
import { TypeIntervenantService } from './type-intervenant.service';

@Injectable()
export class TypeIntervenantPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private typeIntervenantService: TypeIntervenantService

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
                this.typeIntervenantService.find(id)
                    .subscribe((typeIntervenantResponse: HttpResponse<TypeIntervenant>) => {
                        const typeIntervenant: TypeIntervenant = typeIntervenantResponse.body;
                        this.ngbModalRef = this.typeIntervenantModalRef(component, typeIntervenant);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.typeIntervenantModalRef(component, new TypeIntervenant());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    typeIntervenantModalRef(component: Component, typeIntervenant: TypeIntervenant): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.typeIntervenant = typeIntervenant;
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
