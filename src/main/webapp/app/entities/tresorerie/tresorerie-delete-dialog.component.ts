import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Tresorerie } from './tresorerie.model';
import { TresoreriePopupService } from './tresorerie-popup.service';
import { TresorerieService } from './tresorerie.service';

@Component({
    selector: 'jhi-tresorerie-delete-dialog',
    templateUrl: './tresorerie-delete-dialog.component.html'
})
export class TresorerieDeleteDialogComponent {

    tresorerie: Tresorerie;

    constructor(
        private tresorerieService: TresorerieService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.tresorerieService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'tresorerieListModification',
                content: 'Deleted an tresorerie'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-tresorerie-delete-popup',
    template: ''
})
export class TresorerieDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private tresoreriePopupService: TresoreriePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.tresoreriePopupService
                .open(TresorerieDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
