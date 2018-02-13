import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { FraisRecue } from './frais-recue.model';
import { FraisRecuePopupService } from './frais-recue-popup.service';
import { FraisRecueService } from './frais-recue.service';

@Component({
    selector: 'jhi-frais-recue-delete-dialog',
    templateUrl: './frais-recue-delete-dialog.component.html'
})
export class FraisRecueDeleteDialogComponent {

    fraisRecue: FraisRecue;

    constructor(
        private fraisRecueService: FraisRecueService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.fraisRecueService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'fraisRecueListModification',
                content: 'Deleted an fraisRecue'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-frais-recue-delete-popup',
    template: ''
})
export class FraisRecueDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private fraisRecuePopupService: FraisRecuePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.fraisRecuePopupService
                .open(FraisRecueDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
