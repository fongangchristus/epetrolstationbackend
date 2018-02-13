import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { CategorieDepense } from './categorie-depense.model';
import { CategorieDepensePopupService } from './categorie-depense-popup.service';
import { CategorieDepenseService } from './categorie-depense.service';

@Component({
    selector: 'jhi-categorie-depense-delete-dialog',
    templateUrl: './categorie-depense-delete-dialog.component.html'
})
export class CategorieDepenseDeleteDialogComponent {

    categorieDepense: CategorieDepense;

    constructor(
        private categorieDepenseService: CategorieDepenseService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.categorieDepenseService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'categorieDepenseListModification',
                content: 'Deleted an categorieDepense'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-categorie-depense-delete-popup',
    template: ''
})
export class CategorieDepenseDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private categorieDepensePopupService: CategorieDepensePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.categorieDepensePopupService
                .open(CategorieDepenseDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
