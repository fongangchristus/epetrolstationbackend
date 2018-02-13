import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { EntreeCarburant } from './entree-carburant.model';
import { EntreeCarburantService } from './entree-carburant.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-entree-carburant',
    templateUrl: './entree-carburant.component.html'
})
export class EntreeCarburantComponent implements OnInit, OnDestroy {
entreeCarburants: EntreeCarburant[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private entreeCarburantService: EntreeCarburantService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.entreeCarburantService.query().subscribe(
            (res: HttpResponse<EntreeCarburant[]>) => {
                this.entreeCarburants = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInEntreeCarburants();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: EntreeCarburant) {
        return item.id;
    }
    registerChangeInEntreeCarburants() {
        this.eventSubscriber = this.eventManager.subscribe('entreeCarburantListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
