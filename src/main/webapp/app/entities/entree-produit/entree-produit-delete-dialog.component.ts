import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { EntreeProduit } from './entree-produit.model';
import { EntreeProduitPopupService } from './entree-produit-popup.service';
import { EntreeProduitService } from './entree-produit.service';

@Component({
    selector: 'jhi-entree-produit-delete-dialog',
    templateUrl: './entree-produit-delete-dialog.component.html'
})
export class EntreeProduitDeleteDialogComponent {

    entreeProduit: EntreeProduit;

    constructor(
        private entreeProduitService: EntreeProduitService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.entreeProduitService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'entreeProduitListModification',
                content: 'Deleted an entreeProduit'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-entree-produit-delete-popup',
    template: ''
})
export class EntreeProduitDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private entreeProduitPopupService: EntreeProduitPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.entreeProduitPopupService
                .open(EntreeProduitDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
