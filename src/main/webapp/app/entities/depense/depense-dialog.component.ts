import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Depense } from './depense.model';
import { DepensePopupService } from './depense-popup.service';
import { DepenseService } from './depense.service';
import { Tresorerie, TresorerieService } from '../tresorerie';
import { Intervenant, IntervenantService } from '../intervenant';
import { CategorieDepense, CategorieDepenseService } from '../categorie-depense';
import { ModeReglement, ModeReglementService } from '../mode-reglement';

@Component({
    selector: 'jhi-depense-dialog',
    templateUrl: './depense-dialog.component.html'
})
export class DepenseDialogComponent implements OnInit {

    depense: Depense;
    isSaving: boolean;

    hastresros: Tresorerie[];

    hasintervs: Intervenant[];

    uses: CategorieDepense[];

    have: ModeReglement[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private depenseService: DepenseService,
        private tresorerieService: TresorerieService,
        private intervenantService: IntervenantService,
        private categorieDepenseService: CategorieDepenseService,
        private modeReglementService: ModeReglementService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.tresorerieService
            .query({filter: 'depense-is-null'})
            .subscribe((res: HttpResponse<Tresorerie[]>) => {
                if (!this.depense.hastresroId) {
                    this.hastresros = res.body;
                } else {
                    this.tresorerieService
                        .find(this.depense.hastresroId)
                        .subscribe((subRes: HttpResponse<Tresorerie>) => {
                            this.hastresros = [subRes.body].concat(res.body);
                        }, (subRes: HttpErrorResponse) => this.onError(subRes.message));
                }
            }, (res: HttpErrorResponse) => this.onError(res.message));
        this.intervenantService
            .query({filter: 'depense-is-null'})
            .subscribe((res: HttpResponse<Intervenant[]>) => {
                if (!this.depense.hasintervId) {
                    this.hasintervs = res.body;
                } else {
                    this.intervenantService
                        .find(this.depense.hasintervId)
                        .subscribe((subRes: HttpResponse<Intervenant>) => {
                            this.hasintervs = [subRes.body].concat(res.body);
                        }, (subRes: HttpErrorResponse) => this.onError(subRes.message));
                }
            }, (res: HttpErrorResponse) => this.onError(res.message));
        this.categorieDepenseService
            .query({filter: 'depense-is-null'})
            .subscribe((res: HttpResponse<CategorieDepense[]>) => {
                if (!this.depense.useId) {
                    this.uses = res.body;
                } else {
                    this.categorieDepenseService
                        .find(this.depense.useId)
                        .subscribe((subRes: HttpResponse<CategorieDepense>) => {
                            this.uses = [subRes.body].concat(res.body);
                        }, (subRes: HttpErrorResponse) => this.onError(subRes.message));
                }
            }, (res: HttpErrorResponse) => this.onError(res.message));
        this.modeReglementService
            .query({filter: 'depense-is-null'})
            .subscribe((res: HttpResponse<ModeReglement[]>) => {
                if (!this.depense.hasId) {
                    this.have = res.body;
                } else {
                    this.modeReglementService
                        .find(this.depense.hasId)
                        .subscribe((subRes: HttpResponse<ModeReglement>) => {
                            this.have = [subRes.body].concat(res.body);
                        }, (subRes: HttpErrorResponse) => this.onError(subRes.message));
                }
            }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.depense.id !== undefined) {
            this.subscribeToSaveResponse(
                this.depenseService.update(this.depense));
        } else {
            this.subscribeToSaveResponse(
                this.depenseService.create(this.depense));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Depense>>) {
        result.subscribe((res: HttpResponse<Depense>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Depense) {
        this.eventManager.broadcast({ name: 'depenseListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackTresorerieById(index: number, item: Tresorerie) {
        return item.id;
    }

    trackIntervenantById(index: number, item: Intervenant) {
        return item.id;
    }

    trackCategorieDepenseById(index: number, item: CategorieDepense) {
        return item.id;
    }

    trackModeReglementById(index: number, item: ModeReglement) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-depense-popup',
    template: ''
})
export class DepensePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private depensePopupService: DepensePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.depensePopupService
                    .open(DepenseDialogComponent as Component, params['id']);
            } else {
                this.depensePopupService
                    .open(DepenseDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
