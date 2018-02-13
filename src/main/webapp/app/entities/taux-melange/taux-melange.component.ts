import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { TauxMelange } from './taux-melange.model';
import { TauxMelangeService } from './taux-melange.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-taux-melange',
    templateUrl: './taux-melange.component.html'
})
export class TauxMelangeComponent implements OnInit, OnDestroy {
tauxMelanges: TauxMelange[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private tauxMelangeService: TauxMelangeService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.tauxMelangeService.query().subscribe(
            (res: HttpResponse<TauxMelange[]>) => {
                this.tauxMelanges = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInTauxMelanges();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: TauxMelange) {
        return item.id;
    }
    registerChangeInTauxMelanges() {
        this.eventSubscriber = this.eventManager.subscribe('tauxMelangeListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
