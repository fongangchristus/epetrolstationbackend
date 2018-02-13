import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { TauxMelange } from './taux-melange.model';
import { TauxMelangeService } from './taux-melange.service';

@Injectable()
export class TauxMelangePopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private tauxMelangeService: TauxMelangeService

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
                this.tauxMelangeService.find(id)
                    .subscribe((tauxMelangeResponse: HttpResponse<TauxMelange>) => {
                        const tauxMelange: TauxMelange = tauxMelangeResponse.body;
                        this.ngbModalRef = this.tauxMelangeModalRef(component, tauxMelange);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.tauxMelangeModalRef(component, new TauxMelange());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    tauxMelangeModalRef(component: Component, tauxMelange: TauxMelange): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.tauxMelange = tauxMelange;
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
