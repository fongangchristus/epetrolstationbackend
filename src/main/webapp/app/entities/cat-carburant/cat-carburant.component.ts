import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { CatCarburant } from './cat-carburant.model';
import { CatCarburantService } from './cat-carburant.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-cat-carburant',
    templateUrl: './cat-carburant.component.html'
})
export class CatCarburantComponent implements OnInit, OnDestroy {
catCarburants: CatCarburant[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private catCarburantService: CatCarburantService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.catCarburantService.query().subscribe(
            (res: HttpResponse<CatCarburant[]>) => {
                this.catCarburants = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInCatCarburants();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: CatCarburant) {
        return item.id;
    }
    registerChangeInCatCarburants() {
        this.eventSubscriber = this.eventManager.subscribe('catCarburantListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
