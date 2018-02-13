import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { DatePipe } from '@angular/common';
import { SortieMyservice } from './sortie-myservice.model';
import { SortieMyserviceService } from './sortie-myservice.service';

@Injectable()
export class SortieMyservicePopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private sortieMyserviceService: SortieMyserviceService

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
                this.sortieMyserviceService.find(id)
                    .subscribe((sortieMyserviceResponse: HttpResponse<SortieMyservice>) => {
                        const sortieMyservice: SortieMyservice = sortieMyserviceResponse.body;
                        sortieMyservice.date = this.datePipe
                            .transform(sortieMyservice.date, 'yyyy-MM-ddTHH:mm:ss');
                        this.ngbModalRef = this.sortieMyserviceModalRef(component, sortieMyservice);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.sortieMyserviceModalRef(component, new SortieMyservice());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    sortieMyserviceModalRef(component: Component, sortieMyservice: SortieMyservice): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.sortieMyservice = sortieMyservice;
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
