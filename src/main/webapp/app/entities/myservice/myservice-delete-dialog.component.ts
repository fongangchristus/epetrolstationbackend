import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Myservice } from './myservice.model';
import { MyservicePopupService } from './myservice-popup.service';
import { MyserviceService } from './myservice.service';

@Component({
    selector: 'jhi-myservice-delete-dialog',
    templateUrl: './myservice-delete-dialog.component.html'
})
export class MyserviceDeleteDialogComponent {

    myservice: Myservice;

    constructor(
        private myserviceService: MyserviceService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.myserviceService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'myserviceListModification',
                content: 'Deleted an myservice'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-myservice-delete-popup',
    template: ''
})
export class MyserviceDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private myservicePopupService: MyservicePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.myservicePopupService
                .open(MyserviceDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
