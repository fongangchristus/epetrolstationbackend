import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Unite } from './unite.model';
import { UniteService } from './unite.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-unite',
    templateUrl: './unite.component.html'
})
export class UniteComponent implements OnInit, OnDestroy {
unites: Unite[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private uniteService: UniteService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.uniteService.query().subscribe(
            (res: HttpResponse<Unite[]>) => {
                this.unites = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInUnites();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Unite) {
        return item.id;
    }
    registerChangeInUnites() {
        this.eventSubscriber = this.eventManager.subscribe('uniteListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
