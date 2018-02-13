import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Intervenant } from './intervenant.model';
import { IntervenantPopupService } from './intervenant-popup.service';
import { IntervenantService } from './intervenant.service';

@Component({
    selector: 'jhi-intervenant-delete-dialog',
    templateUrl: './intervenant-delete-dialog.component.html'
})
export class IntervenantDeleteDialogComponent {

    intervenant: Intervenant;

    constructor(
        private intervenantService: IntervenantService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.intervenantService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'intervenantListModification',
                content: 'Deleted an intervenant'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-intervenant-delete-popup',
    template: ''
})
export class IntervenantDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private intervenantPopupService: IntervenantPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.intervenantPopupService
                .open(IntervenantDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
