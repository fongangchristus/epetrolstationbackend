import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { EntreeCarburant } from './entree-carburant.model';
import { EntreeCarburantPopupService } from './entree-carburant-popup.service';
import { EntreeCarburantService } from './entree-carburant.service';
import { ModeReglement, ModeReglementService } from '../mode-reglement';
import { Carburant, CarburantService } from '../carburant';
import { Tresorerie, TresorerieService } from '../tresorerie';

@Component({
    selector: 'jhi-entree-carburant-dialog',
    templateUrl: './entree-carburant-dialog.component.html'
})
export class EntreeCarburantDialogComponent implements OnInit {

    entreeCarburant: EntreeCarburant;
    isSaving: boolean;

    modereglements: ModeReglement[];

    carburants: Carburant[];

    tresoreries: Tresorerie[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private entreeCarburantService: EntreeCarburantService,
        private modeReglementService: ModeReglementService,
        private carburantService: CarburantService,
        private tresorerieService: TresorerieService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.modeReglementService
            .query({filter: 'entreecarburant-is-null'})
            .subscribe((res: HttpResponse<ModeReglement[]>) => {
                if (!this.entreeCarburant.modeReglementId) {
                    this.modereglements = res.body;
                } else {
                    this.modeReglementService
                        .find(this.entreeCarburant.modeReglementId)
                        .subscribe((subRes: HttpResponse<ModeReglement>) => {
                            this.modereglements = [subRes.body].concat(res.body);
                        }, (subRes: HttpErrorResponse) => this.onError(subRes.message));
                }
            }, (res: HttpErrorResponse) => this.onError(res.message));
        this.carburantService
            .query({filter: 'entreecarburant-is-null'})
            .subscribe((res: HttpResponse<Carburant[]>) => {
                if (!this.entreeCarburant.carburantId) {
                    this.carburants = res.body;
                } else {
                    this.carburantService
                        .find(this.entreeCarburant.carburantId)
                        .subscribe((subRes: HttpResponse<Carburant>) => {
                            this.carburants = [subRes.body].concat(res.body);
                        }, (subRes: HttpErrorResponse) => this.onError(subRes.message));
                }
            }, (res: HttpErrorResponse) => this.onError(res.message));
        this.tresorerieService
            .query({filter: 'entreecarburant-is-null'})
            .subscribe((res: HttpResponse<Tresorerie[]>) => {
                if (!this.entreeCarburant.tresorerieId) {
                    this.tresoreries = res.body;
                } else {
                    this.tresorerieService
                        .find(this.entreeCarburant.tresorerieId)
                        .subscribe((subRes: HttpResponse<Tresorerie>) => {
                            this.tresoreries = [subRes.body].concat(res.body);
                        }, (subRes: HttpErrorResponse) => this.onError(subRes.message));
                }
            }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.entreeCarburant.id !== undefined) {
            this.subscribeToSaveResponse(
                this.entreeCarburantService.update(this.entreeCarburant));
        } else {
            this.subscribeToSaveResponse(
                this.entreeCarburantService.create(this.entreeCarburant));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<EntreeCarburant>>) {
        result.subscribe((res: HttpResponse<EntreeCarburant>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: EntreeCarburant) {
        this.eventManager.broadcast({ name: 'entreeCarburantListModification', content: 'OK'});
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

    trackCarburantById(index: number, item: Carburant) {
        return item.id;
    }

    trackTresorerieById(index: number, item: Tresorerie) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-entree-carburant-popup',
    template: ''
})
export class EntreeCarburantPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private entreeCarburantPopupService: EntreeCarburantPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.entreeCarburantPopupService
                    .open(EntreeCarburantDialogComponent as Component, params['id']);
            } else {
                this.entreeCarburantPopupService
                    .open(EntreeCarburantDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
