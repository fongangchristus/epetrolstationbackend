import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Pompe } from './pompe.model';
import { PompePopupService } from './pompe-popup.service';
import { PompeService } from './pompe.service';

@Component({
    selector: 'jhi-pompe-delete-dialog',
    templateUrl: './pompe-delete-dialog.component.html'
})
export class PompeDeleteDialogComponent {

    pompe: Pompe;

    constructor(
        private pompeService: PompeService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.pompeService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'pompeListModification',
                content: 'Deleted an pompe'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-pompe-delete-popup',
    template: ''
})
export class PompeDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private pompePopupService: PompePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.pompePopupService
                .open(PompeDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
