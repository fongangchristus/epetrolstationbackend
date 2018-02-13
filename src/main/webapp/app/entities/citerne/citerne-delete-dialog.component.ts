import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Citerne } from './citerne.model';
import { CiternePopupService } from './citerne-popup.service';
import { CiterneService } from './citerne.service';

@Component({
    selector: 'jhi-citerne-delete-dialog',
    templateUrl: './citerne-delete-dialog.component.html'
})
export class CiterneDeleteDialogComponent {

    citerne: Citerne;

    constructor(
        private citerneService: CiterneService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.citerneService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'citerneListModification',
                content: 'Deleted an citerne'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-citerne-delete-popup',
    template: ''
})
export class CiterneDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private citernePopupService: CiternePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.citernePopupService
                .open(CiterneDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
