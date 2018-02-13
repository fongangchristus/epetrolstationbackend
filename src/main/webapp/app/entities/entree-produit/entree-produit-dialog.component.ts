import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { EntreeProduit } from './entree-produit.model';
import { EntreeProduitPopupService } from './entree-produit-popup.service';
import { EntreeProduitService } from './entree-produit.service';
import { ModeReglement, ModeReglementService } from '../mode-reglement';
import { Tresorerie, TresorerieService } from '../tresorerie';
import { Produit, ProduitService } from '../produit';

@Component({
    selector: 'jhi-entree-produit-dialog',
    templateUrl: './entree-produit-dialog.component.html'
})
export class EntreeProduitDialogComponent implements OnInit {

    entreeProduit: EntreeProduit;
    isSaving: boolean;

    moders: ModeReglement[];

    tresoreries: Tresorerie[];

    produits: Produit[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private entreeProduitService: EntreeProduitService,
        private modeReglementService: ModeReglementService,
        private tresorerieService: TresorerieService,
        private produitService: ProduitService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.modeReglementService
            .query({filter: 'entreeproduit-is-null'})
            .subscribe((res: HttpResponse<ModeReglement[]>) => {
                if (!this.entreeProduit.modeRId) {
                    this.moders = res.body;
                } else {
                    this.modeReglementService
                        .find(this.entreeProduit.modeRId)
                        .subscribe((subRes: HttpResponse<ModeReglement>) => {
                            this.moders = [subRes.body].concat(res.body);
                        }, (subRes: HttpErrorResponse) => this.onError(subRes.message));
                }
            }, (res: HttpErrorResponse) => this.onError(res.message));
        this.tresorerieService
            .query({filter: 'entreeproduit-is-null'})
            .subscribe((res: HttpResponse<Tresorerie[]>) => {
                if (!this.entreeProduit.tresorerieId) {
                    this.tresoreries = res.body;
                } else {
                    this.tresorerieService
                        .find(this.entreeProduit.tresorerieId)
                        .subscribe((subRes: HttpResponse<Tresorerie>) => {
                            this.tresoreries = [subRes.body].concat(res.body);
                        }, (subRes: HttpErrorResponse) => this.onError(subRes.message));
                }
            }, (res: HttpErrorResponse) => this.onError(res.message));
        this.produitService
            .query({filter: 'entreeproduit-is-null'})
            .subscribe((res: HttpResponse<Produit[]>) => {
                if (!this.entreeProduit.produitId) {
                    this.produits = res.body;
                } else {
                    this.produitService
                        .find(this.entreeProduit.produitId)
                        .subscribe((subRes: HttpResponse<Produit>) => {
                            this.produits = [subRes.body].concat(res.body);
                        }, (subRes: HttpErrorResponse) => this.onError(subRes.message));
                }
            }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.entreeProduit.id !== undefined) {
            this.subscribeToSaveResponse(
                this.entreeProduitService.update(this.entreeProduit));
        } else {
            this.subscribeToSaveResponse(
                this.entreeProduitService.create(this.entreeProduit));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<EntreeProduit>>) {
        result.subscribe((res: HttpResponse<EntreeProduit>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: EntreeProduit) {
        this.eventManager.broadcast({ name: 'entreeProduitListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackModeReglementById(index: number, item: ModeReglement) {
        return item.id;
    }

    trackTresorerieById(index: number, item: Tresorerie) {
        return item.id;
    }

    trackProduitById(index: number, item: Produit) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-entree-produit-popup',
    template: ''
})
export class EntreeProduitPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private entreeProduitPopupService: EntreeProduitPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.entreeProduitPopupService
                    .open(EntreeProduitDialogComponent as Component, params['id']);
            } else {
                this.entreeProduitPopupService
                    .open(EntreeProduitDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
