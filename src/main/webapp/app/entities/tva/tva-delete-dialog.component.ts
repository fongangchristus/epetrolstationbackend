import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Tva } from './tva.model';
import { TvaPopupService } from './tva-popup.service';
import { TvaService } from './tva.service';

@Component({
    selector: 'jhi-tva-delete-dialog',
    templateUrl: './tva-delete-dialog.component.html'
})
export class TvaDeleteDialogComponent {

    tva: Tva;

    constructor(
        private tvaService: TvaService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.tvaService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'tvaListModification',
                content: 'Deleted an tva'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-tva-delete-popup',
    template: ''
})
export class TvaDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private tvaPopupService: TvaPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.tvaPopupService
                .open(TvaDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
