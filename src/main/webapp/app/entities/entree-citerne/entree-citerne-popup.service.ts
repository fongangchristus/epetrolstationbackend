import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { DatePipe } from '@angular/common';
import { EntreeCiterne } from './entree-citerne.model';
import { EntreeCiterneService } from './entree-citerne.service';

@Injectable()
export class EntreeCiternePopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private entreeCiterneService: EntreeCiterneService

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
                this.entreeCiterneService.find(id)
                    .subscribe((entreeCiterneResponse: HttpResponse<EntreeCiterne>) => {
                        const entreeCiterne: EntreeCiterne = entreeCiterneResponse.body;
                        entreeCiterne.date = this.datePipe
                            .transform(entreeCiterne.date, 'yyyy-MM-ddTHH:mm:ss');
                        this.ngbModalRef = this.entreeCiterneModalRef(component, entreeCiterne);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.entreeCiterneModalRef(component, new EntreeCiterne());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    entreeCiterneModalRef(component: Component, entreeCiterne: EntreeCiterne): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.entreeCiterne = entreeCiterne;
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
