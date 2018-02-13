import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { SortieMyservice } from './sortie-myservice.model';
import { SortieMyservicePopupService } from './sortie-myservice-popup.service';
import { SortieMyserviceService } from './sortie-myservice.service';
import { Myservice, MyserviceService } from '../myservice';
import { Intervenant, IntervenantService } from '../intervenant';
import { ModeReglement, ModeReglementService } from '../mode-reglement';
import { Tresorerie, TresorerieService } from '../tresorerie';

@Component({
    selector: 'jhi-sortie-myservice-dialog',
    templateUrl: './sortie-myservice-dialog.component.html'
})
export class SortieMyserviceDialogComponent implements OnInit {

    sortieMyservice: SortieMyservice;
    isSaving: boolean;

    servs: Myservice[];

    hasinters: Intervenant[];

    hasmoders: ModeReglement[];

    hastresors: Tresorerie[];

    hastres: Tresorerie[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private sortieMyserviceService: SortieMyserviceService,
        private myserviceService: MyserviceService,
        private intervenantService: IntervenantService,
        private modeReglementService: ModeReglementService,
        private tresorerieService: TresorerieService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.myserviceService
            .query({filter: 'sortiemyservice-is-null'})
            .subscribe((res: HttpResponse<Myservice[]>) => {
                if (!this.sortieMyservice.servId) {
                    this.servs = res.body;
                } else {
                    this.myserviceService
                        .find(this.sortieMyservice.servId)
                        .subscribe((subRes: HttpResponse<Myservice>) => {
                            this.servs = [subRes.body].concat(res.body);
                        }, (subRes: HttpErrorResponse) => this.onError(subRes.message));
                }
            }, (res: HttpErrorResponse) => this.onError(res.message));
        this.intervenantService
            .query({filter: 'sortiemyservice-is-null'})
            .subscribe((res: HttpResponse<Intervenant[]>) => {
                if (!this.sortieMyservice.hasinterId) {
                    this.hasinters = res.body;
                } else {
                    this.intervenantService
                        .find(this.sortieMyservice.hasinterId)
                        .subscribe((subRes: HttpResponse<Intervenant>) => {
                            this.hasinters = [subRes.body].concat(res.body);
                        }, (subRes: HttpErrorResponse) => this.onError(subRes.message));
                }
            }, (res: HttpErrorResponse) => this.onError(res.message));
        this.modeReglementService
            .query({filter: 'sortiemyservice-is-null'})
            .subscribe((res: HttpResponse<ModeReglement[]>) => {
                if (!this.sortieMyservice.hasModeRId) {
                    this.hasmoders = res.body;
                } else {
                    this.modeReglementService
                        .find(this.sortieMyservice.hasModeRId)
                        .subscribe((subRes: HttpResponse<ModeReglement>) => {
                            this.hasmoders = [subRes.body].concat(res.body);
                        }, (subRes: HttpErrorResponse) => this.onError(subRes.message));
                }
            }, (res: HttpErrorResponse) => this.onError(res.message));
        this.tresorerieService
            .query({filter: 'sortiemyservice-is-null'})
            .subscribe((res: HttpResponse<Tresorerie[]>) => {
                if (!this.sortieMyservice.hastresorId) {
                    this.hastresors = res.body;
                } else {
                    this.tresorerieService
                        .find(this.sortieMyservice.hastresorId)
                        .subscribe((subRes: HttpResponse<Tresorerie>) => {
                            this.hastresors = [subRes.body].concat(res.body);
                        }, (subRes: HttpErrorResponse) => this.onError(subRes.message));
                }
            }, (res: HttpErrorResponse) => this.onError(res.message));
        this.tresorerieService
            .query({filter: 'sortiemyservice-is-null'})
            .subscribe((res: HttpResponse<Tresorerie[]>) => {
                if (!this.sortieMyservice.hastreId) {
                    this.hastres = res.body;
                } else {
                    this.tresorerieService
                        .find(this.sortieMyservice.hastreId)
                        .subscribe((subRes: HttpResponse<Tresorerie>) => {
                            this.hastres = [subRes.body].concat(res.body);
                        }, (subRes: HttpErrorResponse) => this.onError(subRes.message));
                }
            }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.sortieMyservice.id !== undefined) {
            this.subscribeToSaveResponse(
                this.sortieMyserviceService.update(this.sortieMyservice));
        } else {
            this.subscribeToSaveResponse(
                this.sortieMyserviceService.create(this.sortieMyservice));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<SortieMyservice>>) {
        result.subscribe((res: HttpResponse<SortieMyservice>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: SortieMyservice) {
        this.eventManager.broadcast({ name: 'sortieMyserviceListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackMyserviceById(index: number, item: Myservice) {
        return item.id;
    }

    trackIntervenantById(index: number, item: Intervenant) {
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
    selector: 'jhi-sortie-myservice-popup',
    template: ''
})
export class SortieMyservicePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private sortieMyservicePopupService: SortieMyservicePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.sortieMyservicePopupService
                    .open(SortieMyserviceDialogComponent as Component, params['id']);
            } else {
                this.sortieMyservicePopupService
                    .open(SortieMyserviceDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
