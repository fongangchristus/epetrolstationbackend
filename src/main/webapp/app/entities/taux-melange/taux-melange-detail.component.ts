import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { TauxMelange } from './taux-melange.model';
import { TauxMelangeService } from './taux-melange.service';

@Component({
    selector: 'jhi-taux-melange-detail',
    templateUrl: './taux-melange-detail.component.html'
})
export class TauxMelangeDetailComponent implements OnInit, OnDestroy {

    tauxMelange: TauxMelange;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private tauxMelangeService: TauxMelangeService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInTauxMelanges();
    }

    load(id) {
        this.tauxMelangeService.find(id)
            .subscribe((tauxMelangeResponse: HttpResponse<TauxMelange>) => {
                this.tauxMelange = tauxMelangeResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInTauxMelanges() {
        this.eventSubscriber = this.eventManager.subscribe(
            'tauxMelangeListModification',
            (response) => this.load(this.tauxMelange.id)
        );
    }
}
