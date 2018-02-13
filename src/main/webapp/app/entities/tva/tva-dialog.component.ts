import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Tva } from './tva.model';
import { TvaPopupService } from './tva-popup.service';
import { TvaService } from './tva.service';

@Component({
    selector: 'jhi-tva-dialog',
    templateUrl: './tva-dialog.component.html'
})
export class TvaDialogComponent implements OnInit {

    tva: Tva;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private tvaService: TvaService,
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
        if (this.tva.id !== undefined) {
            this.subscribeToSaveResponse(
                this.tvaService.update(this.tva));
        } else {
            this.subscribeToSaveResponse(
                this.tvaService.create(this.tva));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Tva>>) {
        result.subscribe((res: HttpResponse<Tva>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Tva) {
        this.eventManager.broadcast({ name: 'tvaListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-tva-popup',
    template: ''
})
export class TvaPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private tvaPopupService: TvaPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.tvaPopupService
                    .open(TvaDialogComponent as Component, params['id']);
            } else {
                this.tvaPopupService
                    .open(TvaDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
