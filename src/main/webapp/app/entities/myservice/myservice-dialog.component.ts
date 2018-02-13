import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Myservice } from './myservice.model';
import { MyservicePopupService } from './myservice-popup.service';
import { MyserviceService } from './myservice.service';

@Component({
    selector: 'jhi-myservice-dialog',
    templateUrl: './myservice-dialog.component.html'
})
export class MyserviceDialogComponent implements OnInit {

    myservice: Myservice;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private myserviceService: MyserviceService,
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
        if (this.myservice.id !== undefined) {
            this.subscribeToSaveResponse(
                this.myserviceService.update(this.myservice));
        } else {
            this.subscribeToSaveResponse(
                this.myserviceService.create(this.myservice));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Myservice>>) {
        result.subscribe((res: HttpResponse<Myservice>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Myservice) {
        this.eventManager.broadcast({ name: 'myserviceListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-myservice-popup',
    template: ''
})
export class MyservicePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private myservicePopupService: MyservicePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.myservicePopupService
                    .open(MyserviceDialogComponent as Component, params['id']);
            } else {
                this.myservicePopupService
                    .open(MyserviceDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
