import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { DatePipe } from '@angular/common';
import { EntreeCarburant } from './entree-carburant.model';
import { EntreeCarburantService } from './entree-carburant.service';

@Injectable()
export class EntreeCarburantPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private entreeCarburantService: EntreeCarburantService

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
                this.entreeCarburantService.find(id)
                    .subscribe((entreeCarburantResponse: HttpResponse<EntreeCarburant>) => {
                        const entreeCarburant: EntreeCarburant = entreeCarburantResponse.body;
                        entreeCarburant.date = this.datePipe
                            .transform(entreeCarburant.date, 'yyyy-MM-ddTHH:mm:ss');
                        this.ngbModalRef = this.entreeCarburantModalRef(component, entreeCarburant);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.entreeCarburantModalRef(component, new EntreeCarburant());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    entreeCarburantModalRef(component: Component, entreeCarburant: EntreeCarburant): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.entreeCarburant = entreeCarburant;
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
