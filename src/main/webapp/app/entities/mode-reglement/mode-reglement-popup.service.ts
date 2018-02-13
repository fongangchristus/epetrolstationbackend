import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { ModeReglement } from './mode-reglement.model';
import { ModeReglementService } from './mode-reglement.service';

@Injectable()
export class ModeReglementPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private modeReglementService: ModeReglementService

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
                this.modeReglementService.find(id)
                    .subscribe((modeReglementResponse: HttpResponse<ModeReglement>) => {
                        const modeReglement: ModeReglement = modeReglementResponse.body;
                        this.ngbModalRef = this.modeReglementModalRef(component, modeReglement);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.modeReglementModalRef(component, new ModeReglement());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    modeReglementModalRef(component: Component, modeReglement: ModeReglement): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.modeReglement = modeReglement;
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
