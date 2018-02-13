import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { EntreeCiterne } from './entree-citerne.model';
import { EntreeCiternePopupService } from './entree-citerne-popup.service';
import { EntreeCiterneService } from './entree-citerne.service';
import { Citerne, CiterneService } from '../citerne';
import { Unite, UniteService } from '../unite';
import { Intervenant, IntervenantService } from '../intervenant';

@Component({
    selector: 'jhi-entree-citerne-dialog',
    templateUrl: './entree-citerne-dialog.component.html'
})
export class EntreeCiterneDialogComponent implements OnInit {

    entreeCiterne: EntreeCiterne;
    isSaving: boolean;

    hasciternes: Citerne[];

    hasunites: Unite[];

    hasis: Intervenant[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private entreeCiterneService: EntreeCiterneService,
        private citerneService: CiterneService,
        private uniteService: UniteService,
        private intervenantService: IntervenantService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.citerneService
            .query({filter: 'entreeciterne-is-null'})
            .subscribe((res: HttpResponse<Citerne[]>) => {
                if (!this.entreeCiterne.hasciterneId) {
                    this.hasciternes = res.body;
                } else {
                    this.citerneService
                        .find(this.entreeCiterne.hasciterneId)
                        .subscribe((subRes: HttpResponse<Citerne>) => {
                            this.hasciternes = [subRes.body].concat(res.body);
                        }, (subRes: HttpErrorResponse) => this.onError(subRes.message));
                }
            }, (res: HttpErrorResponse) => this.onError(res.message));
        this.uniteService
            .query({filter: 'entreeciterne-is-null'})
            .subscribe((res: HttpResponse<Unite[]>) => {
                if (!this.entreeCiterne.hasuniteId) {
                    this.hasunites = res.body;
                } else {
                    this.uniteService
                        .find(this.entreeCiterne.hasuniteId)
                        .subscribe((subRes: HttpResponse<Unite>) => {
                            this.hasunites = [subRes.body].concat(res.body);
                        }, (subRes: HttpErrorResponse) => this.onError(subRes.message));
                }
            }, (res: HttpErrorResponse) => this.onError(res.message));
        this.intervenantService
            .query({filter: 'entreeciterne-is-null'})
            .subscribe((res: HttpResponse<Intervenant[]>) => {
                if (!this.entreeCiterne.hasiId) {
                    this.hasis = res.body;
                } else {
                    this.intervenantService
                        .find(this.entreeCiterne.hasiId)
                        .subscribe((subRes: HttpResponse<Intervenant>) => {
                            this.hasis = [subRes.body].concat(res.body);
                        }, (subRes: HttpErrorResponse) => this.onError(subRes.message));
                }
            }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.entreeCiterne.id !== undefined) {
            this.subscribeToSaveResponse(
                this.entreeCiterneService.update(this.entreeCiterne));
        } else {
            this.subscribeToSaveResponse(
                this.entreeCiterneService.create(this.entreeCiterne));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<EntreeCiterne>>) {
        result.subscribe((res: HttpResponse<EntreeCiterne>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: EntreeCiterne) {
        this.eventManager.broadcast({ name: 'entreeCiterneListModification', content: 'OK'});
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

    trackUniteById(index: number, item: Unite) {
        return item.id;
    }

    trackIntervenantById(index: number, item: Intervenant) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-entree-citerne-popup',
    template: ''
})
export class EntreeCiternePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private entreeCiternePopupService: EntreeCiternePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.entreeCiternePopupService
                    .open(EntreeCiterneDialogComponent as Component, params['id']);
            } else {
                this.entreeCiternePopupService
                    .open(EntreeCiterneDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
