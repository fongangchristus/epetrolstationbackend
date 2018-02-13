import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Tresorerie } from './tresorerie.model';
import { TresorerieService } from './tresorerie.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-tresorerie',
    templateUrl: './tresorerie.component.html'
})
export class TresorerieComponent implements OnInit, OnDestroy {
tresoreries: Tresorerie[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private tresorerieService: TresorerieService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.tresorerieService.query().subscribe(
            (res: HttpResponse<Tresorerie[]>) => {
                this.tresoreries = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInTresoreries();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Tresorerie) {
        return item.id;
    }
    registerChangeInTresoreries() {
        this.eventSubscriber = this.eventManager.subscribe('tresorerieListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
