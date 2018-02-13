import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Depense } from './depense.model';
import { DepenseService } from './depense.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-depense',
    templateUrl: './depense.component.html'
})
export class DepenseComponent implements OnInit, OnDestroy {
depenses: Depense[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private depenseService: DepenseService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.depenseService.query().subscribe(
            (res: HttpResponse<Depense[]>) => {
                this.depenses = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInDepenses();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Depense) {
        return item.id;
    }
    registerChangeInDepenses() {
        this.eventSubscriber = this.eventManager.subscribe('depenseListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
