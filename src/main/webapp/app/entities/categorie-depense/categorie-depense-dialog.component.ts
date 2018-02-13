import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { CategorieDepense } from './categorie-depense.model';
import { CategorieDepensePopupService } from './categorie-depense-popup.service';
import { CategorieDepenseService } from './categorie-depense.service';

@Component({
    selector: 'jhi-categorie-depense-dialog',
    templateUrl: './categorie-depense-dialog.component.html'
})
export class CategorieDepenseDialogComponent implements OnInit {

    categorieDepense: CategorieDepense;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private categorieDepenseService: CategorieDepenseService,
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
        if (this.categorieDepense.id !== undefined) {
            this.subscribeToSaveResponse(
                this.categorieDepenseService.update(this.categorieDepense));
        } else {
            this.subscribeToSaveResponse(
                this.categorieDepenseService.create(this.categorieDepense));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<CategorieDepense>>) {
        result.subscribe((res: HttpResponse<CategorieDepense>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: CategorieDepense) {
        this.eventManager.broadcast({ name: 'categorieDepenseListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-categorie-depense-popup',
    template: ''
})
export class CategorieDepensePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private categorieDepensePopupService: CategorieDepensePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.categorieDepensePopupService
                    .open(CategorieDepenseDialogComponent as Component, params['id']);
            } else {
                this.categorieDepensePopupService
                    .open(CategorieDepenseDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
