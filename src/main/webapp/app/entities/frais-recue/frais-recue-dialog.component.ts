import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { FraisRecue } from './frais-recue.model';
import { FraisRecuePopupService } from './frais-recue-popup.service';
import { FraisRecueService } from './frais-recue.service';
import { Tresorerie, TresorerieService } from '../tresorerie';

@Component({
    selector: 'jhi-frais-recue-dialog',
    templateUrl: './frais-recue-dialog.component.html'
})
export class FraisRecueDialogComponent implements OnInit {

    fraisRecue: FraisRecue;
    isSaving: boolean;

    hastrrs: Tresorerie[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private fraisRecueService: FraisRecueService,
        private tresorerieService: TresorerieService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.tresorerieService
            .query({filter: 'fraisrecue-is-null'})
            .subscribe((res: HttpResponse<Tresorerie[]>) => {
                if (!this.fraisRecue.hastrrId) {
                    this.hastrrs = res.body;
                } else {
                    this.tresorerieService
                        .find(this.fraisRecue.hastrrId)
                        .subscribe((subRes: HttpResponse<Tresorerie>) => {
                            this.hastrrs = [subRes.body].concat(res.body);
                        }, (subRes: HttpErrorResponse) => this.onError(subRes.message));
                }
            }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.fraisRecue.id !== undefined) {
            this.subscribeToSaveResponse(
                this.fraisRecueService.update(this.fraisRecue));
        } else {
            this.subscribeToSaveResponse(
                this.fraisRecueService.create(this.fraisRecue));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<FraisRecue>>) {
        result.subscribe((res: HttpResponse<FraisRecue>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: FraisRecue) {
        this.eventManager.broadcast({ name: 'fraisRecueListModification', content: 'OK'});
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
}

@Component({
    selector: 'jhi-frais-recue-popup',
    template: ''
})
export class FraisRecuePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private fraisRecuePopupService: FraisRecuePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.fraisRecuePopupService
                    .open(FraisRecueDialogComponent as Component, params['id']);
            } else {
                this.fraisRecuePopupService
                    .open(FraisRecueDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
