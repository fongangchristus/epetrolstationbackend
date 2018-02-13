import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { CategorieDepense } from './categorie-depense.model';
import { CategorieDepenseService } from './categorie-depense.service';

@Injectable()
export class CategorieDepensePopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private categorieDepenseService: CategorieDepenseService

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
                this.categorieDepenseService.find(id)
                    .subscribe((categorieDepenseResponse: HttpResponse<CategorieDepense>) => {
                        const categorieDepense: CategorieDepense = categorieDepenseResponse.body;
                        this.ngbModalRef = this.categorieDepenseModalRef(component, categorieDepense);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.categorieDepenseModalRef(component, new CategorieDepense());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    categorieDepenseModalRef(component: Component, categorieDepense: CategorieDepense): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.categorieDepense = categorieDepense;
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
