import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { EntreeCiterne } from './entree-citerne.model';
import { EntreeCiternePopupService } from './entree-citerne-popup.service';
import { EntreeCiterneService } from './entree-citerne.service';

@Component({
    selector: 'jhi-entree-citerne-delete-dialog',
    templateUrl: './entree-citerne-delete-dialog.component.html'
})
export class EntreeCiterneDeleteDialogComponent {

    entreeCiterne: EntreeCiterne;

    constructor(
        private entreeCiterneService: EntreeCiterneService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.entreeCiterneService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'entreeCiterneListModification',
                content: 'Deleted an entreeCiterne'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-entree-citerne-delete-popup',
    template: ''
})
export class EntreeCiterneDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private entreeCiternePopupService: EntreeCiternePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.entreeCiternePopupService
                .open(EntreeCiterneDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
