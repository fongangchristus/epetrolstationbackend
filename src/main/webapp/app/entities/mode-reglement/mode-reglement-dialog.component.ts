import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ModeReglement } from './mode-reglement.model';
import { ModeReglementPopupService } from './mode-reglement-popup.service';
import { ModeReglementService } from './mode-reglement.service';

@Component({
    selector: 'jhi-mode-reglement-dialog',
    templateUrl: './mode-reglement-dialog.component.html'
})
export class ModeReglementDialogComponent implements OnInit {

    modeReglement: ModeReglement;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private modeReglementService: ModeReglementService,
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
        if (this.modeReglement.id !== undefined) {
            this.subscribeToSaveResponse(
                this.modeReglementService.update(this.modeReglement));
        } else {
            this.subscribeToSaveResponse(
                this.modeReglementService.create(this.modeReglement));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ModeReglement>>) {
        result.subscribe((res: HttpResponse<ModeReglement>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: ModeReglement) {
        this.eventManager.broadcast({ name: 'modeReglementListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-mode-reglement-popup',
    template: ''
})
export class ModeReglementPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private modeReglementPopupService: ModeReglementPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modeReglementPopupService
                    .open(ModeReglementDialogComponent as Component, params['id']);
            } else {
                this.modeReglementPopupService
                    .open(ModeReglementDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
