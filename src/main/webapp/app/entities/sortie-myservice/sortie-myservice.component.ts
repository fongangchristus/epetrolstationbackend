import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { SortieMyservice } from './sortie-myservice.model';
import { SortieMyserviceService } from './sortie-myservice.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-sortie-myservice',
    templateUrl: './sortie-myservice.component.html'
})
export class SortieMyserviceComponent implements OnInit, OnDestroy {
sortieMyservices: SortieMyservice[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private sortieMyserviceService: SortieMyserviceService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.sortieMyserviceService.query().subscribe(
            (res: HttpResponse<SortieMyservice[]>) => {
                this.sortieMyservices = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInSortieMyservices();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: SortieMyservice) {
        return item.id;
    }
    registerChangeInSortieMyservices() {
        this.eventSubscriber = this.eventManager.subscribe('sortieMyserviceListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
