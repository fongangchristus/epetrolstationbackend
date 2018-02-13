import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { SortieProduit } from './sortie-produit.model';
import { SortieProduitPopupService } from './sortie-produit-popup.service';
import { SortieProduitService } from './sortie-produit.service';

@Component({
    selector: 'jhi-sortie-produit-delete-dialog',
    templateUrl: './sortie-produit-delete-dialog.component.html'
})
export class SortieProduitDeleteDialogComponent {

    sortieProduit: SortieProduit;

    constructor(
        private sortieProduitService: SortieProduitService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.sortieProduitService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'sortieProduitListModification',
                content: 'Deleted an sortieProduit'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-sortie-produit-delete-popup',
    template: ''
})
export class SortieProduitDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private sortieProduitPopupService: SortieProduitPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.sortieProduitPopupService
                .open(SortieProduitDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
