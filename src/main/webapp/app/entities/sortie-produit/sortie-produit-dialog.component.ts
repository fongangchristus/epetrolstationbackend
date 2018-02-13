import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { SortieProduit } from './sortie-produit.model';
import { SortieProduitPopupService } from './sortie-produit-popup.service';
import { SortieProduitService } from './sortie-produit.service';
import { ModeReglement, ModeReglementService } from '../mode-reglement';
import { Intervenant, IntervenantService } from '../intervenant';
import { Tresorerie, TresorerieService } from '../tresorerie';
import { Produit, ProduitService } from '../produit';

@Component({
    selector: 'jhi-sortie-produit-dialog',
    templateUrl: './sortie-produit-dialog.component.html'
})
export class SortieProduitDialogComponent implements OnInit {

    sortieProduit: SortieProduit;
    isSaving: boolean;

    moderegs: ModeReglement[];

    clients: Intervenant[];

    tresors: Tresorerie[];

    prods: Produit[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private sortieProduitService: SortieProduitService,
        private modeReglementService: ModeReglementService,
        private intervenantService: IntervenantService,
        private tresorerieService: TresorerieService,
        private produitService: ProduitService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.modeReglementService
            .query({filter: 'sortieproduit-is-null'})
            .subscribe((res: HttpResponse<ModeReglement[]>) => {
                if (!this.sortieProduit.modeRegId) {
                    this.moderegs = res.body;
                } else {
                    this.modeReglementService
                        .find(this.sortieProduit.modeRegId)
                        .subscribe((subRes: HttpResponse<ModeReglement>) => {
                            this.moderegs = [subRes.body].concat(res.body);
                        }, (subRes: HttpErrorResponse) => this.onError(subRes.message));
                }
            }, (res: HttpErrorResponse) => this.onError(res.message));
        this.intervenantService
            .query({filter: 'sortieproduit-is-null'})
            .subscribe((res: HttpResponse<Intervenant[]>) => {
                if (!this.sortieProduit.clientId) {
                    this.clients = res.body;
                } else {
                    this.intervenantService
                        .find(this.sortieProduit.clientId)
                        .subscribe((subRes: HttpResponse<Intervenant>) => {
                            this.clients = [subRes.body].concat(res.body);
                        }, (subRes: HttpErrorResponse) => this.onError(subRes.message));
                }
            }, (res: HttpErrorResponse) => this.onError(res.message));
        this.tresorerieService
            .query({filter: 'sortieproduit-is-null'})
            .subscribe((res: HttpResponse<Tresorerie[]>) => {
                if (!this.sortieProduit.tresorId) {
                    this.tresors = res.body;
                } else {
                    this.tresorerieService
                        .find(this.sortieProduit.tresorId)
                        .subscribe((subRes: HttpResponse<Tresorerie>) => {
                            this.tresors = [subRes.body].concat(res.body);
                        }, (subRes: HttpErrorResponse) => this.onError(subRes.message));
                }
            }, (res: HttpErrorResponse) => this.onError(res.message));
        this.produitService
            .query({filter: 'sortieproduit-is-null'})
            .subscribe((res: HttpResponse<Produit[]>) => {
                if (!this.sortieProduit.prodId) {
                    this.prods = res.body;
                } else {
                    this.produitService
                        .find(this.sortieProduit.prodId)
                        .subscribe((subRes: HttpResponse<Produit>) => {
                            this.prods = [subRes.body].concat(res.body);
                        }, (subRes: HttpErrorResponse) => this.onError(subRes.message));
                }
            }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.sortieProduit.id !== undefined) {
            this.subscribeToSaveResponse(
                this.sortieProduitService.update(this.sortieProduit));
        } else {
            this.subscribeToSaveResponse(
                this.sortieProduitService.create(this.sortieProduit));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<SortieProduit>>) {
        result.subscribe((res: HttpResponse<SortieProduit>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: SortieProduit) {
        this.eventManager.broadcast({ name: 'sortieProduitListModification', content: 'OK'});
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

    trackIntervenantById(index: number, item: Intervenant) {
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
    selector: 'jhi-sortie-produit-popup',
    template: ''
})
export class SortieProduitPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private sortieProduitPopupService: SortieProduitPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.sortieProduitPopupService
                    .open(SortieProduitDialogComponent as Component, params['id']);
            } else {
                this.sortieProduitPopupService
                    .open(SortieProduitDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
