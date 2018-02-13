import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Tresorerie } from './tresorerie.model';
import { TresoreriePopupService } from './tresorerie-popup.service';
import { TresorerieService } from './tresorerie.service';
import { TypeTresorerie, TypeTresorerieService } from '../type-tresorerie';

@Component({
    selector: 'jhi-tresorerie-dialog',
    templateUrl: './tresorerie-dialog.component.html'
})
export class TresorerieDialogComponent implements OnInit {

    tresorerie: Tresorerie;
    isSaving: boolean;

    hastypets: TypeTresorerie[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private tresorerieService: TresorerieService,
        private typeTresorerieService: TypeTresorerieService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.typeTresorerieService
            .query({filter: 'tresorerie-is-null'})
            .subscribe((res: HttpResponse<TypeTresorerie[]>) => {
                if (!this.tresorerie.hastypetId) {
                    this.hastypets = res.body;
                } else {
                    this.typeTresorerieService
                        .find(this.tresorerie.hastypetId)
                        .subscribe((subRes: HttpResponse<TypeTresorerie>) => {
                            this.hastypets = [subRes.body].concat(res.body);
                        }, (subRes: HttpErrorResponse) => this.onError(subRes.message));
                }
            }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.tresorerie.id !== undefined) {
            this.subscribeToSaveResponse(
                this.tresorerieService.update(this.tresorerie));
        } else {
            this.subscribeToSaveResponse(
                this.tresorerieService.create(this.tresorerie));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Tresorerie>>) {
        result.subscribe((res: HttpResponse<Tresorerie>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Tresorerie) {
        this.eventManager.broadcast({ name: 'tresorerieListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackTypeTresorerieById(index: number, item: TypeTresorerie) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-tresorerie-popup',
    template: ''
})
export class TresoreriePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private tresoreriePopupService: TresoreriePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.tresoreriePopupService
                    .open(TresorerieDialogComponent as Component, params['id']);
            } else {
                this.tresoreriePopupService
                    .open(TresorerieDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
