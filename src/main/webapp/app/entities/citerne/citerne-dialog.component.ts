import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Citerne } from './citerne.model';
import { CiternePopupService } from './citerne-popup.service';
import { CiterneService } from './citerne.service';
import { CatCarburant, CatCarburantService } from '../cat-carburant';
import { Unite, UniteService } from '../unite';
import { Station, StationService } from '../station';

@Component({
    selector: 'jhi-citerne-dialog',
    templateUrl: './citerne-dialog.component.html'
})
export class CiterneDialogComponent implements OnInit {

    citerne: Citerne;
    isSaving: boolean;

    hascs: CatCarburant[];

    hasus: Unite[];

    hais: Station[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private citerneService: CiterneService,
        private catCarburantService: CatCarburantService,
        private uniteService: UniteService,
        private stationService: StationService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.catCarburantService
            .query({filter: 'citerne-is-null'})
            .subscribe((res: HttpResponse<CatCarburant[]>) => {
                if (!this.citerne.hascId) {
                    this.hascs = res.body;
                } else {
                    this.catCarburantService
                        .find(this.citerne.hascId)
                        .subscribe((subRes: HttpResponse<CatCarburant>) => {
                            this.hascs = [subRes.body].concat(res.body);
                        }, (subRes: HttpErrorResponse) => this.onError(subRes.message));
                }
            }, (res: HttpErrorResponse) => this.onError(res.message));
        this.uniteService
            .query({filter: 'citerne-is-null'})
            .subscribe((res: HttpResponse<Unite[]>) => {
                if (!this.citerne.hasuId) {
                    this.hasus = res.body;
                } else {
                    this.uniteService
                        .find(this.citerne.hasuId)
                        .subscribe((subRes: HttpResponse<Unite>) => {
                            this.hasus = [subRes.body].concat(res.body);
                        }, (subRes: HttpErrorResponse) => this.onError(subRes.message));
                }
            }, (res: HttpErrorResponse) => this.onError(res.message));
        this.stationService
            .query({filter: 'citerne-is-null'})
            .subscribe((res: HttpResponse<Station[]>) => {
                if (!this.citerne.haiId) {
                    this.hais = res.body;
                } else {
                    this.stationService
                        .find(this.citerne.haiId)
                        .subscribe((subRes: HttpResponse<Station>) => {
                            this.hais = [subRes.body].concat(res.body);
                        }, (subRes: HttpErrorResponse) => this.onError(subRes.message));
                }
            }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.citerne.id !== undefined) {
            this.subscribeToSaveResponse(
                this.citerneService.update(this.citerne));
        } else {
            this.subscribeToSaveResponse(
                this.citerneService.create(this.citerne));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Citerne>>) {
        result.subscribe((res: HttpResponse<Citerne>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Citerne) {
        this.eventManager.broadcast({ name: 'citerneListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackCatCarburantById(index: number, item: CatCarburant) {
        return item.id;
    }

    trackUniteById(index: number, item: Unite) {
        return item.id;
    }

    trackStationById(index: number, item: Station) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-citerne-popup',
    template: ''
})
export class CiternePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private citernePopupService: CiternePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.citernePopupService
                    .open(CiterneDialogComponent as Component, params['id']);
            } else {
                this.citernePopupService
                    .open(CiterneDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
