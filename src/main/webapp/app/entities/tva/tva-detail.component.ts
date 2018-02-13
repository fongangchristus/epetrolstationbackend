import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Tva } from './tva.model';
import { TvaService } from './tva.service';

@Component({
    selector: 'jhi-tva-detail',
    templateUrl: './tva-detail.component.html'
})
export class TvaDetailComponent implements OnInit, OnDestroy {

    tva: Tva;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private tvaService: TvaService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInTvas();
    }

    load(id) {
        this.tvaService.find(id)
            .subscribe((tvaResponse: HttpResponse<Tva>) => {
                this.tva = tvaResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInTvas() {
        this.eventSubscriber = this.eventManager.subscribe(
            'tvaListModification',
            (response) => this.load(this.tva.id)
        );
    }
}
