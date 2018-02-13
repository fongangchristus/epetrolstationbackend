import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { TypeIntervenant } from './type-intervenant.model';
import { TypeIntervenantService } from './type-intervenant.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-type-intervenant',
    templateUrl: './type-intervenant.component.html'
})
export class TypeIntervenantComponent implements OnInit, OnDestroy {
typeIntervenants: TypeIntervenant[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private typeIntervenantService: TypeIntervenantService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.typeIntervenantService.query().subscribe(
            (res: HttpResponse<TypeIntervenant[]>) => {
                this.typeIntervenants = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInTypeIntervenants();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: TypeIntervenant) {
        return item.id;
    }
    registerChangeInTypeIntervenants() {
        this.eventSubscriber = this.eventManager.subscribe('typeIntervenantListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
