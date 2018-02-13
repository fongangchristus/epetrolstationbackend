import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { TauxMelange } from './taux-melange.model';
import { TauxMelangePopupService } from './taux-melange-popup.service';
import { TauxMelangeService } from './taux-melange.service';

@Component({
    selector: 'jhi-taux-melange-delete-dialog',
    templateUrl: './taux-melange-delete-dialog.component.html'
})
export class TauxMelangeDeleteDialogComponent {

    tauxMelange: TauxMelange;

    constructor(
        private tauxMelangeService: TauxMelangeService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.tauxMelangeService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'tauxMelangeListModification',
                content: 'Deleted an tauxMelange'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-taux-melange-delete-popup',
    template: ''
})
export class TauxMelangeDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private tauxMelangePopupService: TauxMelangePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.tauxMelangePopupService
                .open(TauxMelangeDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
