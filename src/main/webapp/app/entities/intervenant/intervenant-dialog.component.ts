import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Intervenant } from './intervenant.model';
import { IntervenantPopupService } from './intervenant-popup.service';
import { IntervenantService } from './intervenant.service';
import { EntreeProduit, EntreeProduitService } from '../entree-produit';
import { EntreeCarburant, EntreeCarburantService } from '../entree-carburant';
import { TypeIntervenant, TypeIntervenantService } from '../type-intervenant';
import { User, UserService } from '../../shared';
import { Station, StationService } from '../station';

@Component({
    selector: 'jhi-intervenant-dialog',
    templateUrl: './intervenant-dialog.component.html'
})
export class IntervenantDialogComponent implements OnInit {

    intervenant: Intervenant;
    isSaving: boolean;

    entreeproduits: EntreeProduit[];

    entreecarburants: EntreeCarburant[];

    managers: Intervenant[];

    typeintervenants: TypeIntervenant[];

    users: User[];

    stations: Station[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private intervenantService: IntervenantService,
        private entreeProduitService: EntreeProduitService,
        private entreeCarburantService: EntreeCarburantService,
        private typeIntervenantService: TypeIntervenantService,
        private userService: UserService,
        private stationService: StationService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.entreeProduitService.query()
            .subscribe((res: HttpResponse<EntreeProduit[]>) => { this.entreeproduits = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.entreeCarburantService.query()
            .subscribe((res: HttpResponse<EntreeCarburant[]>) => { this.entreecarburants = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.intervenantService
            .query({filter: 'intervenant-is-null'})
            .subscribe((res: HttpResponse<Intervenant[]>) => {
                if (!this.intervenant.managerId) {
                    this.managers = res.body;
                } else {
                    this.intervenantService
                        .find(this.intervenant.managerId)
                        .subscribe((subRes: HttpResponse<Intervenant>) => {
                            this.managers = [subRes.body].concat(res.body);
                        }, (subRes: HttpErrorResponse) => this.onError(subRes.message));
                }
            }, (res: HttpErrorResponse) => this.onError(res.message));
        this.typeIntervenantService
            .query({filter: 'intervenant-is-null'})
            .subscribe((res: HttpResponse<TypeIntervenant[]>) => {
                if (!this.intervenant.typeIntervenantId) {
                    this.typeintervenants = res.body;
                } else {
                    this.typeIntervenantService
                        .find(this.intervenant.typeIntervenantId)
                        .subscribe((subRes: HttpResponse<TypeIntervenant>) => {
                            this.typeintervenants = [subRes.body].concat(res.body);
                        }, (subRes: HttpErrorResponse) => this.onError(subRes.message));
                }
            }, (res: HttpErrorResponse) => this.onError(res.message));
        this.userService.query()
            .subscribe((res: HttpResponse<User[]>) => { this.users = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.stationService.query()
            .subscribe((res: HttpResponse<Station[]>) => { this.stations = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.intervenant.id !== undefined) {
            this.subscribeToSaveResponse(
                this.intervenantService.update(this.intervenant));
        } else {
            this.subscribeToSaveResponse(
                this.intervenantService.create(this.intervenant));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Intervenant>>) {
        result.subscribe((res: HttpResponse<Intervenant>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Intervenant) {
        this.eventManager.broadcast({ name: 'intervenantListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackEntreeProduitById(index: number, item: EntreeProduit) {
        return item.id;
    }

    trackEntreeCarburantById(index: number, item: EntreeCarburant) {
        return item.id;
    }

    trackIntervenantById(index: number, item: Intervenant) {
        return item.id;
    }

    trackTypeIntervenantById(index: number, item: TypeIntervenant) {
        return item.id;
    }

    trackUserById(index: number, item: User) {
        return item.id;
    }

    trackStationById(index: number, item: Station) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-intervenant-popup',
    template: ''
})
export class IntervenantPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private intervenantPopupService: IntervenantPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.intervenantPopupService
                    .open(IntervenantDialogComponent as Component, params['id']);
            } else {
                this.intervenantPopupService
                    .open(IntervenantDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
