import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { CatCarburant } from './cat-carburant.model';
import { CatCarburantPopupService } from './cat-carburant-popup.service';
import { CatCarburantService } from './cat-carburant.service';

@Component({
    selector: 'jhi-cat-carburant-dialog',
    templateUrl: './cat-carburant-dialog.component.html'
})
export class CatCarburantDialogComponent implements OnInit {

    catCarburant: CatCarburant;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private catCarburantService: CatCarburantService,
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
        if (this.catCarburant.id !== undefined) {
            this.subscribeToSaveResponse(
                this.catCarburantService.update(this.catCarburant));
        } else {
            this.subscribeToSaveResponse(
                this.catCarburantService.create(this.catCarburant));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<CatCarburant>>) {
        result.subscribe((res: HttpResponse<CatCarburant>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: CatCarburant) {
        this.eventManager.broadcast({ name: 'catCarburantListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-cat-carburant-popup',
    template: ''
})
export class CatCarburantPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private catCarburantPopupService: CatCarburantPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.catCarburantPopupService
                    .open(CatCarburantDialogComponent as Component, params['id']);
            } else {
                this.catCarburantPopupService
                    .open(CatCarburantDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
