import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { CatCarburant } from './cat-carburant.model';
import { CatCarburantPopupService } from './cat-carburant-popup.service';
import { CatCarburantService } from './cat-carburant.service';

@Component({
    selector: 'jhi-cat-carburant-delete-dialog',
    templateUrl: './cat-carburant-delete-dialog.component.html'
})
export class CatCarburantDeleteDialogComponent {

    catCarburant: CatCarburant;

    constructor(
        private catCarburantService: CatCarburantService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.catCarburantService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'catCarburantListModification',
                content: 'Deleted an catCarburant'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-cat-carburant-delete-popup',
    template: ''
})
export class CatCarburantDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private catCarburantPopupService: CatCarburantPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.catCarburantPopupService
                .open(CatCarburantDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
