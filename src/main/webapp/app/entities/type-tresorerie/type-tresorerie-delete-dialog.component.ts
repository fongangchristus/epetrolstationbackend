import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { TypeTresorerie } from './type-tresorerie.model';
import { TypeTresoreriePopupService } from './type-tresorerie-popup.service';
import { TypeTresorerieService } from './type-tresorerie.service';

@Component({
    selector: 'jhi-type-tresorerie-delete-dialog',
    templateUrl: './type-tresorerie-delete-dialog.component.html'
})
export class TypeTresorerieDeleteDialogComponent {

    typeTresorerie: TypeTresorerie;

    constructor(
        private typeTresorerieService: TypeTresorerieService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.typeTresorerieService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'typeTresorerieListModification',
                content: 'Deleted an typeTresorerie'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-type-tresorerie-delete-popup',
    template: ''
})
export class TypeTresorerieDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private typeTresoreriePopupService: TypeTresoreriePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.typeTresoreriePopupService
                .open(TypeTresorerieDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
