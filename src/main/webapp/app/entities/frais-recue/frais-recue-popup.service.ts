import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { DatePipe } from '@angular/common';
import { FraisRecue } from './frais-recue.model';
import { FraisRecueService } from './frais-recue.service';

@Injectable()
export class FraisRecuePopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private fraisRecueService: FraisRecueService

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
                this.fraisRecueService.find(id)
                    .subscribe((fraisRecueResponse: HttpResponse<FraisRecue>) => {
                        const fraisRecue: FraisRecue = fraisRecueResponse.body;
                        fraisRecue.date = this.datePipe
                            .transform(fraisRecue.date, 'yyyy-MM-ddTHH:mm:ss');
                        this.ngbModalRef = this.fraisRecueModalRef(component, fraisRecue);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.fraisRecueModalRef(component, new FraisRecue());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    fraisRecueModalRef(component: Component, fraisRecue: FraisRecue): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.fraisRecue = fraisRecue;
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
