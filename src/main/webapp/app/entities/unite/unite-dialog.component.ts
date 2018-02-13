import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Unite } from './unite.model';
import { UnitePopupService } from './unite-popup.service';
import { UniteService } from './unite.service';

@Component({
    selector: 'jhi-unite-dialog',
    templateUrl: './unite-dialog.component.html'
})
export class UniteDialogComponent implements OnInit {

    unite: Unite;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private uniteService: UniteService,
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
        if (this.unite.id !== undefined) {
            this.subscribeToSaveResponse(
                this.uniteService.update(this.unite));
        } else {
            this.subscribeToSaveResponse(
                this.uniteService.create(this.unite));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Unite>>) {
        result.subscribe((res: HttpResponse<Unite>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Unite) {
        this.eventManager.broadcast({ name: 'uniteListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-unite-popup',
    template: ''
})
export class UnitePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private unitePopupService: UnitePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.unitePopupService
                    .open(UniteDialogComponent as Component, params['id']);
            } else {
                this.unitePopupService
                    .open(UniteDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
