import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Tva } from './tva.model';
import { TvaService } from './tva.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-tva',
    templateUrl: './tva.component.html'
})
export class TvaComponent implements OnInit, OnDestroy {
tvas: Tva[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private tvaService: TvaService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.tvaService.query().subscribe(
            (res: HttpResponse<Tva[]>) => {
                this.tvas = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInTvas();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Tva) {
        return item.id;
    }
    registerChangeInTvas() {
        this.eventSubscriber = this.eventManager.subscribe('tvaListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
