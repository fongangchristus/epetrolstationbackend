import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Produit } from './produit.model';
import { ProduitPopupService } from './produit-popup.service';
import { ProduitService } from './produit.service';
import { Tva, TvaService } from '../tva';
import { Categorie, CategorieService } from '../categorie';

@Component({
    selector: 'jhi-produit-dialog',
    templateUrl: './produit-dialog.component.html'
})
export class ProduitDialogComponent implements OnInit {

    produit: Produit;
    isSaving: boolean;

    tvas: Tva[];

    categories: Categorie[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private produitService: ProduitService,
        private tvaService: TvaService,
        private categorieService: CategorieService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.tvaService
            .query({filter: 'produit-is-null'})
            .subscribe((res: HttpResponse<Tva[]>) => {
                if (!this.produit.tvaId) {
                    this.tvas = res.body;
                } else {
                    this.tvaService
                        .find(this.produit.tvaId)
                        .subscribe((subRes: HttpResponse<Tva>) => {
                            this.tvas = [subRes.body].concat(res.body);
                        }, (subRes: HttpErrorResponse) => this.onError(subRes.message));
                }
            }, (res: HttpErrorResponse) => this.onError(res.message));
        this.categorieService.query()
            .subscribe((res: HttpResponse<Categorie[]>) => { this.categories = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.produit.id !== undefined) {
            this.subscribeToSaveResponse(
                this.produitService.update(this.produit));
        } else {
            this.subscribeToSaveResponse(
                this.produitService.create(this.produit));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Produit>>) {
        result.subscribe((res: HttpResponse<Produit>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Produit) {
        this.eventManager.broadcast({ name: 'produitListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackTvaById(index: number, item: Tva) {
        return item.id;
    }

    trackCategorieById(index: number, item: Categorie) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-produit-popup',
    template: ''
})
export class ProduitPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private produitPopupService: ProduitPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.produitPopupService
                    .open(ProduitDialogComponent as Component, params['id']);
            } else {
                this.produitPopupService
                    .open(ProduitDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
