import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ModeReglement } from './mode-reglement.model';
import { ModeReglementPopupService } from './mode-reglement-popup.service';
import { ModeReglementService } from './mode-reglement.service';

@Component({
    selector: 'jhi-mode-reglement-delete-dialog',
    templateUrl: './mode-reglement-delete-dialog.component.html'
})
export class ModeReglementDeleteDialogComponent {

    modeReglement: ModeReglement;

    constructor(
        private modeReglementService: ModeReglementService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.modeReglementService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'modeReglementListModification',
                content: 'Deleted an modeReglement'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-mode-reglement-delete-popup',
    template: ''
})
export class ModeReglementDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private modeReglementPopupService: ModeReglementPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modeReglementPopupService
                .open(ModeReglementDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
