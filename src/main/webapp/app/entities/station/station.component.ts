import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Station } from './station.model';
import { StationService } from './station.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-station',
    templateUrl: './station.component.html'
})
export class StationComponent implements OnInit, OnDestroy {
stations: Station[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private stationService: StationService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.stationService.query().subscribe(
            (res: HttpResponse<Station[]>) => {
                this.stations = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInStations();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Station) {
        return item.id;
    }
    registerChangeInStations() {
        this.eventSubscriber = this.eventManager.subscribe('stationListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
