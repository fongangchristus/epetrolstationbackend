import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { TypeIntervenant } from './type-intervenant.model';
import { TypeIntervenantPopupService } from './type-intervenant-popup.service';
import { TypeIntervenantService } from './type-intervenant.service';

@Component({
    selector: 'jhi-type-intervenant-dialog',
    templateUrl: './type-intervenant-dialog.component.html'
})
export class TypeIntervenantDialogComponent implements OnInit {

    typeIntervenant: TypeIntervenant;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private typeIntervenantService: TypeIntervenantService,
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
        if (this.typeIntervenant.id !== undefined) {
            this.subscribeToSaveResponse(
                this.typeIntervenantService.update(this.typeIntervenant));
        } else {
            this.subscribeToSaveResponse(
                this.typeIntervenantService.create(this.typeIntervenant));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<TypeIntervenant>>) {
        result.subscribe((res: HttpResponse<TypeIntervenant>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: TypeIntervenant) {
        this.eventManager.broadcast({ name: 'typeIntervenantListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-type-intervenant-popup',
    template: ''
})
export class TypeIntervenantPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private typeIntervenantPopupService: TypeIntervenantPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.typeIntervenantPopupService
                    .open(TypeIntervenantDialogComponent as Component, params['id']);
            } else {
                this.typeIntervenantPopupService
                    .open(TypeIntervenantDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
