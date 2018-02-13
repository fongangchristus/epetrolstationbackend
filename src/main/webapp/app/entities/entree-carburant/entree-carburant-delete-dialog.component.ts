import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { EntreeCarburant } from './entree-carburant.model';
import { EntreeCarburantPopupService } from './entree-carburant-popup.service';
import { EntreeCarburantService } from './entree-carburant.service';

@Component({
    selector: 'jhi-entree-carburant-delete-dialog',
    templateUrl: './entree-carburant-delete-dialog.component.html'
})
export class EntreeCarburantDeleteDialogComponent {

    entreeCarburant: EntreeCarburant;

    constructor(
        private entreeCarburantService: EntreeCarburantService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.entreeCarburantService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'entreeCarburantListModification',
                content: 'Deleted an entreeCarburant'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-entree-carburant-delete-popup',
    template: ''
})
export class EntreeCarburantDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private entreeCarburantPopupService: EntreeCarburantPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.entreeCarburantPopupService
                .open(EntreeCarburantDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
