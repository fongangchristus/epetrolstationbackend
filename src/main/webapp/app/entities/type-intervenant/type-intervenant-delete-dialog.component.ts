import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { TypeIntervenant } from './type-intervenant.model';
import { TypeIntervenantPopupService } from './type-intervenant-popup.service';
import { TypeIntervenantService } from './type-intervenant.service';

@Component({
    selector: 'jhi-type-intervenant-delete-dialog',
    templateUrl: './type-intervenant-delete-dialog.component.html'
})
export class TypeIntervenantDeleteDialogComponent {

    typeIntervenant: TypeIntervenant;

    constructor(
        private typeIntervenantService: TypeIntervenantService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.typeIntervenantService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'typeIntervenantListModification',
                content: 'Deleted an typeIntervenant'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-type-intervenant-delete-popup',
    template: ''
})
export class TypeIntervenantDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private typeIntervenantPopupService: TypeIntervenantPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.typeIntervenantPopupService
                .open(TypeIntervenantDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
