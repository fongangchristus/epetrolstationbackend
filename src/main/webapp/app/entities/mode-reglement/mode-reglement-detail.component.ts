import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { ModeReglement } from './mode-reglement.model';
import { ModeReglementService } from './mode-reglement.service';

@Component({
    selector: 'jhi-mode-reglement-detail',
    templateUrl: './mode-reglement-detail.component.html'
})
export class ModeReglementDetailComponent implements OnInit, OnDestroy {

    modeReglement: ModeReglement;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private modeReglementService: ModeReglementService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInModeReglements();
    }

    load(id) {
        this.modeReglementService.find(id)
            .subscribe((modeReglementResponse: HttpResponse<ModeReglement>) => {
                this.modeReglement = modeReglementResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInModeReglements() {
        this.eventSubscriber = this.eventManager.subscribe(
            'modeReglementListModification',
            (response) => this.load(this.modeReglement.id)
        );
    }
}
