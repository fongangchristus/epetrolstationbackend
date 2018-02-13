import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Pompe } from './pompe.model';
import { PompeService } from './pompe.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-pompe',
    templateUrl: './pompe.component.html'
})
export class PompeComponent implements OnInit, OnDestroy {
pompes: Pompe[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private pompeService: PompeService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.pompeService.query().subscribe(
            (res: HttpResponse<Pompe[]>) => {
                this.pompes = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInPompes();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Pompe) {
        return item.id;
    }
    registerChangeInPompes() {
        this.eventSubscriber = this.eventManager.subscribe('pompeListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
