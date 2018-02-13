import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { SortieCarburant } from './sortie-carburant.model';
import { SortieCarburantPopupService } from './sortie-carburant-popup.service';
import { SortieCarburantService } from './sortie-carburant.service';

@Component({
    selector: 'jhi-sortie-carburant-delete-dialog',
    templateUrl: './sortie-carburant-delete-dialog.component.html'
})
export class SortieCarburantDeleteDialogComponent {

    sortieCarburant: SortieCarburant;

    constructor(
        private sortieCarburantService: SortieCarburantService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.sortieCarburantService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'sortieCarburantListModification',
                content: 'Deleted an sortieCarburant'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-sortie-carburant-delete-popup',
    template: ''
})
export class SortieCarburantDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private sortieCarburantPopupService: SortieCarburantPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.sortieCarburantPopupService
                .open(SortieCarburantDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
