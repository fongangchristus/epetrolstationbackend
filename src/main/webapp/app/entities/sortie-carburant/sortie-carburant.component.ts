import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { SortieCarburant } from './sortie-carburant.model';
import { SortieCarburantService } from './sortie-carburant.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-sortie-carburant',
    templateUrl: './sortie-carburant.component.html'
})
export class SortieCarburantComponent implements OnInit, OnDestroy {
sortieCarburants: SortieCarburant[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private sortieCarburantService: SortieCarburantService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.sortieCarburantService.query().subscribe(
            (res: HttpResponse<SortieCarburant[]>) => {
                this.sortieCarburants = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInSortieCarburants();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: SortieCarburant) {
        return item.id;
    }
    registerChangeInSortieCarburants() {
        this.eventSubscriber = this.eventManager.subscribe('sortieCarburantListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
