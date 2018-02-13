import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { TauxMelange } from './taux-melange.model';
import { TauxMelangePopupService } from './taux-melange-popup.service';
import { TauxMelangeService } from './taux-melange.service';

@Component({
    selector: 'jhi-taux-melange-dialog',
    templateUrl: './taux-melange-dialog.component.html'
})
export class TauxMelangeDialogComponent implements OnInit {

    tauxMelange: TauxMelange;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private tauxMelangeService: TauxMelangeService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.tauxMelange.id !== undefined) {
            this.subscribeToSaveResponse(
                this.tauxMelangeService.update(this.tauxMelange));
        } else {
            this.subscribeToSaveResponse(
                this.tauxMelangeService.create(this.tauxMelange));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<TauxMelange>>) {
        result.subscribe((res: HttpResponse<TauxMelange>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: TauxMelange) {
        this.eventManager.broadcast({ name: 'tauxMelangeListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-taux-melange-popup',
    template: ''
})
export class TauxMelangePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private tauxMelangePopupService: TauxMelangePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.tauxMelangePopupService
                    .open(TauxMelangeDialogComponent as Component, params['id']);
            } else {
                this.tauxMelangePopupService
                    .open(TauxMelangeDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
