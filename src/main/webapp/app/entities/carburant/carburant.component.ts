import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Carburant } from './carburant.model';
import { CarburantService } from './carburant.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-carburant',
    templateUrl: './carburant.component.html'
})
export class CarburantComponent implements OnInit, OnDestroy {
carburants: Carburant[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private carburantService: CarburantService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.carburantService.query().subscribe(
            (res: HttpResponse<Carburant[]>) => {
                this.carburants = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInCarburants();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Carburant) {
        return item.id;
    }
    registerChangeInCarburants() {
        this.eventSubscriber = this.eventManager.subscribe('carburantListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
