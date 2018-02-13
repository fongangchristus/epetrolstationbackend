import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Pompe } from './pompe.model';
import { PompePopupService } from './pompe-popup.service';
import { PompeService } from './pompe.service';
import { Citerne, CiterneService } from '../citerne';
import { Reservoir, ReservoirService } from '../reservoir';
import { TauxMelange, TauxMelangeService } from '../taux-melange';
import { CatCarburant, CatCarburantService } from '../cat-carburant';

@Component({
    selector: 'jhi-pompe-dialog',
    templateUrl: './pompe-dialog.component.html'
})
export class PompeDialogComponent implements OnInit {

    pompe: Pompe;
    isSaving: boolean;

    hascis: Citerne[];

    hasres: Reservoir[];

    hastas: TauxMelange[];

    hascas: CatCarburant[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private pompeService: PompeService,
        private citerneService: CiterneService,
        private reservoirService: ReservoirService,
        private tauxMelangeService: TauxMelangeService,
        private catCarburantService: CatCarburantService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.citerneService
            .query({filter: 'pompe-is-null'})
            .subscribe((res: HttpResponse<Citerne[]>) => {
                if (!this.pompe.hasciId) {
                    this.hascis = res.body;
                } else {
                    this.citerneService
                        .find(this.pompe.hasciId)
                        .subscribe((subRes: HttpResponse<Citerne>) => {
                            this.hascis = [subRes.body].concat(res.body);
                        }, (subRes: HttpErrorResponse) => this.onError(subRes.message));
                }
            }, (res: HttpErrorResponse) => this.onError(res.message));
        this.reservoirService
            .query({filter: 'pompe-is-null'})
            .subscribe((res: HttpResponse<Reservoir[]>) => {
                if (!this.pompe.hasreId) {
                    this.hasres = res.body;
                } else {
                    this.reservoirService
                        .find(this.pompe.hasreId)
                        .subscribe((subRes: HttpResponse<Reservoir>) => {
                            this.hasres = [subRes.body].concat(res.body);
                        }, (subRes: HttpErrorResponse) => this.onError(subRes.message));
                }
            }, (res: HttpErrorResponse) => this.onError(res.message));
        this.tauxMelangeService
            .query({filter: 'pompe-is-null'})
            .subscribe((res: HttpResponse<TauxMelange[]>) => {
                if (!this.pompe.hastaId) {
                    this.hastas = res.body;
                } else {
                    this.tauxMelangeService
                        .find(this.pompe.hastaId)
                        .subscribe((subRes: HttpResponse<TauxMelange>) => {
                            this.hastas = [subRes.body].concat(res.body);
                        }, (subRes: HttpErrorResponse) => this.onError(subRes.message));
                }
            }, (res: HttpErrorResponse) => this.onError(res.message));
        this.catCarburantService
            .query({filter: 'pompe-is-null'})
            .subscribe((res: HttpResponse<CatCarburant[]>) => {
                if (!this.pompe.hascaId) {
                    this.hascas = res.body;
                } else {
                    this.catCarburantService
                        .find(this.pompe.hascaId)
                        .subscribe((subRes: HttpResponse<CatCarburant>) => {
                            this.hascas = [subRes.body].concat(res.body);
                        }, (subRes: HttpErrorResponse) => this.onError(subRes.message));
                }
            }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.pompe.id !== undefined) {
            this.subscribeToSaveResponse(
                this.pompeService.update(this.pompe));
        } else {
            this.subscribeToSaveResponse(
                this.pompeService.create(this.pompe));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Pompe>>) {
        result.subscribe((res: HttpResponse<Pompe>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Pompe) {
        this.eventManager.broadcast({ name: 'pompeListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackCiterneById(index: number, item: Citerne) {
        return item.id;
    }

    trackReservoirById(index: number, item: Reservoir) {
        return item.id;
    }

    trackTauxMelangeById(index: number, item: TauxMelange) {
        return item.id;
    }

    trackCatCarburantById(index: number, item: CatCarburant) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-pompe-popup',
    template: ''
})
export class PompePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private pompePopupService: PompePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.pompePopupService
                    .open(PompeDialogComponent as Component, params['id']);
            } else {
                this.pompePopupService
                    .open(PompeDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
