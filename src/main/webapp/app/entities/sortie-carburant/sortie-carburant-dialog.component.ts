import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { SortieCarburant } from './sortie-carburant.model';
import { SortieCarburantPopupService } from './sortie-carburant-popup.service';
import { SortieCarburantService } from './sortie-carburant.service';
import { Intervenant, IntervenantService } from '../intervenant';
import { Carburant, CarburantService } from '../carburant';
import { Pompe, PompeService } from '../pompe';
import { ModeReglement, ModeReglementService } from '../mode-reglement';
import { Tresorerie, TresorerieService } from '../tresorerie';

@Component({
    selector: 'jhi-sortie-carburant-dialog',
    templateUrl: './sortie-carburant-dialog.component.html'
})
export class SortieCarburantDialogComponent implements OnInit {

    sortieCarburant: SortieCarburant;
    isSaving: boolean;

    inters: Intervenant[];

    carbs: Carburant[];

    pomps: Pompe[];

    modes: ModeReglement[];

    tres: Tresorerie[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private sortieCarburantService: SortieCarburantService,
        private intervenantService: IntervenantService,
        private carburantService: CarburantService,
        private pompeService: PompeService,
        private modeReglementService: ModeReglementService,
        private tresorerieService: TresorerieService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.intervenantService
            .query({filter: 'sortiecarburant-is-null'})
            .subscribe((res: HttpResponse<Intervenant[]>) => {
                if (!this.sortieCarburant.interId) {
                    this.inters = res.body;
                } else {
                    this.intervenantService
                        .find(this.sortieCarburant.interId)
                        .subscribe((subRes: HttpResponse<Intervenant>) => {
                            this.inters = [subRes.body].concat(res.body);
                        }, (subRes: HttpErrorResponse) => this.onError(subRes.message));
                }
            }, (res: HttpErrorResponse) => this.onError(res.message));
        this.carburantService
            .query({filter: 'sortiecarburant-is-null'})
            .subscribe((res: HttpResponse<Carburant[]>) => {
                if (!this.sortieCarburant.carbId) {
                    this.carbs = res.body;
                } else {
                    this.carburantService
                        .find(this.sortieCarburant.carbId)
                        .subscribe((subRes: HttpResponse<Carburant>) => {
                            this.carbs = [subRes.body].concat(res.body);
                        }, (subRes: HttpErrorResponse) => this.onError(subRes.message));
                }
            }, (res: HttpErrorResponse) => this.onError(res.message));
        this.pompeService
            .query({filter: 'sortiecarburant-is-null'})
            .subscribe((res: HttpResponse<Pompe[]>) => {
                if (!this.sortieCarburant.pompId) {
                    this.pomps = res.body;
                } else {
                    this.pompeService
                        .find(this.sortieCarburant.pompId)
                        .subscribe((subRes: HttpResponse<Pompe>) => {
                            this.pomps = [subRes.body].concat(res.body);
                        }, (subRes: HttpErrorResponse) => this.onError(subRes.message));
                }
            }, (res: HttpErrorResponse) => this.onError(res.message));
        this.modeReglementService
            .query({filter: 'sortiecarburant-is-null'})
            .subscribe((res: HttpResponse<ModeReglement[]>) => {
                if (!this.sortieCarburant.modeId) {
                    this.modes = res.body;
                } else {
                    this.modeReglementService
                        .find(this.sortieCarburant.modeId)
                        .subscribe((subRes: HttpResponse<ModeReglement>) => {
                            this.modes = [subRes.body].concat(res.body);
                        }, (subRes: HttpErrorResponse) => this.onError(subRes.message));
                }
            }, (res: HttpErrorResponse) => this.onError(res.message));
        this.tresorerieService
            .query({filter: 'sortiecarburant-is-null'})
            .subscribe((res: HttpResponse<Tresorerie[]>) => {
                if (!this.sortieCarburant.tresId) {
                    this.tres = res.body;
                } else {
                    this.tresorerieService
                        .find(this.sortieCarburant.tresId)
                        .subscribe((subRes: HttpResponse<Tresorerie>) => {
                            this.tres = [subRes.body].concat(res.body);
                        }, (subRes: HttpErrorResponse) => this.onError(subRes.message));
                }
            }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.sortieCarburant.id !== undefined) {
            this.subscribeToSaveResponse(
                this.sortieCarburantService.update(this.sortieCarburant));
        } else {
            this.subscribeToSaveResponse(
                this.sortieCarburantService.create(this.sortieCarburant));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<SortieCarburant>>) {
        result.subscribe((res: HttpResponse<SortieCarburant>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: SortieCarburant) {
        this.eventManager.broadcast({ name: 'sortieCarburantListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackIntervenantById(index: number, item: Intervenant) {
        return item.id;
    }

    trackCarburantById(index: number, item: Carburant) {
        return item.id;
    }

    trackPompeById(index: number, item: Pompe) {
        return item.id;
    }

    trackModeReglementById(index: number, item: ModeReglement) {
        return item.id;
    }

    trackTresorerieById(index: number, item: Tresorerie) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-sortie-carburant-popup',
    template: ''
})
export class SortieCarburantPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private sortieCarburantPopupService: SortieCarburantPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.sortieCarburantPopupService
                    .open(SortieCarburantDialogComponent as Component, params['id']);
            } else {
                this.sortieCarburantPopupService
                    .open(SortieCarburantDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
