import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { FraisRecue } from './frais-recue.model';
import { FraisRecueService } from './frais-recue.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-frais-recue',
    templateUrl: './frais-recue.component.html'
})
export class FraisRecueComponent implements OnInit, OnDestroy {
fraisRecues: FraisRecue[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private fraisRecueService: FraisRecueService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.fraisRecueService.query().subscribe(
            (res: HttpResponse<FraisRecue[]>) => {
                this.fraisRecues = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInFraisRecues();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: FraisRecue) {
        return item.id;
    }
    registerChangeInFraisRecues() {
        this.eventSubscriber = this.eventManager.subscribe('fraisRecueListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
