import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { TypeTresorerie } from './type-tresorerie.model';
import { TypeTresoreriePopupService } from './type-tresorerie-popup.service';
import { TypeTresorerieService } from './type-tresorerie.service';

@Component({
    selector: 'jhi-type-tresorerie-dialog',
    templateUrl: './type-tresorerie-dialog.component.html'
})
export class TypeTresorerieDialogComponent implements OnInit {

    typeTresorerie: TypeTresorerie;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private typeTresorerieService: TypeTresorerieService,
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
        if (this.typeTresorerie.id !== undefined) {
            this.subscribeToSaveResponse(
                this.typeTresorerieService.update(this.typeTresorerie));
        } else {
            this.subscribeToSaveResponse(
                this.typeTresorerieService.create(this.typeTresorerie));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<TypeTresorerie>>) {
        result.subscribe((res: HttpResponse<TypeTresorerie>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: TypeTresorerie) {
        this.eventManager.broadcast({ name: 'typeTresorerieListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-type-tresorerie-popup',
    template: ''
})
export class TypeTresoreriePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private typeTresoreriePopupService: TypeTresoreriePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.typeTresoreriePopupService
                    .open(TypeTresorerieDialogComponent as Component, params['id']);
            } else {
                this.typeTresoreriePopupService
                    .open(TypeTresorerieDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
