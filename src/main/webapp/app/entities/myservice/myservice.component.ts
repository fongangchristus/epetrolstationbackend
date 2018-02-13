import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Myservice } from './myservice.model';
import { MyserviceService } from './myservice.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-myservice',
    templateUrl: './myservice.component.html'
})
export class MyserviceComponent implements OnInit, OnDestroy {
myservices: Myservice[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private myserviceService: MyserviceService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.myserviceService.query().subscribe(
            (res: HttpResponse<Myservice[]>) => {
                this.myservices = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInMyservices();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Myservice) {
        return item.id;
    }
    registerChangeInMyservices() {
        this.eventSubscriber = this.eventManager.subscribe('myserviceListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
