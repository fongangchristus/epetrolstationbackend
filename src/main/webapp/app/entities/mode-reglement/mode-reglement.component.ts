import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ModeReglement } from './mode-reglement.model';
import { ModeReglementService } from './mode-reglement.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-mode-reglement',
    templateUrl: './mode-reglement.component.html'
})
export class ModeReglementComponent implements OnInit, OnDestroy {
modeReglements: ModeReglement[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private modeReglementService: ModeReglementService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.modeReglementService.query().subscribe(
            (res: HttpResponse<ModeReglement[]>) => {
                this.modeReglements = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInModeReglements();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ModeReglement) {
        return item.id;
    }
    registerChangeInModeReglements() {
        this.eventSubscriber = this.eventManager.subscribe('modeReglementListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
