import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { DatePipe } from '@angular/common';
import { EntreeProduit } from './entree-produit.model';
import { EntreeProduitService } from './entree-produit.service';

@Injectable()
export class EntreeProduitPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private entreeProduitService: EntreeProduitService

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
                this.entreeProduitService.find(id)
                    .subscribe((entreeProduitResponse: HttpResponse<EntreeProduit>) => {
                        const entreeProduit: EntreeProduit = entreeProduitResponse.body;
                        entreeProduit.date = this.datePipe
                            .transform(entreeProduit.date, 'yyyy-MM-ddTHH:mm:ss');
                        this.ngbModalRef = this.entreeProduitModalRef(component, entreeProduit);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.entreeProduitModalRef(component, new EntreeProduit());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    entreeProduitModalRef(component: Component, entreeProduit: EntreeProduit): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.entreeProduit = entreeProduit;
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
