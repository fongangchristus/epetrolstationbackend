import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Unite } from './unite.model';
import { UnitePopupService } from './unite-popup.service';
import { UniteService } from './unite.service';

@Component({
    selector: 'jhi-unite-delete-dialog',
    templateUrl: './unite-delete-dialog.component.html'
})
export class UniteDeleteDialogComponent {

    unite: Unite;

    constructor(
        private uniteService: UniteService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.uniteService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'uniteListModification',
                content: 'Deleted an unite'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-unite-delete-popup',
    template: ''
})
export class UniteDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private unitePopupService: UnitePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.unitePopupService
                .open(UniteDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
