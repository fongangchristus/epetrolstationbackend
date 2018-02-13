import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { SortieMyservice } from './sortie-myservice.model';
import { SortieMyservicePopupService } from './sortie-myservice-popup.service';
import { SortieMyserviceService } from './sortie-myservice.service';

@Component({
    selector: 'jhi-sortie-myservice-delete-dialog',
    templateUrl: './sortie-myservice-delete-dialog.component.html'
})
export class SortieMyserviceDeleteDialogComponent {

    sortieMyservice: SortieMyservice;

    constructor(
        private sortieMyserviceService: SortieMyserviceService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.sortieMyserviceService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'sortieMyserviceListModification',
                content: 'Deleted an sortieMyservice'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-sortie-myservice-delete-popup',
    template: ''
})
export class SortieMyserviceDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private sortieMyservicePopupService: SortieMyservicePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.sortieMyservicePopupService
                .open(SortieMyserviceDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
