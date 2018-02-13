import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Pompe } from './pompe.model';
import { PompeService } from './pompe.service';

@Component({
    selector: 'jhi-pompe-detail',
    templateUrl: './pompe-detail.component.html'
})
export class PompeDetailComponent implements OnInit, OnDestroy {

    pompe: Pompe;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private pompeService: PompeService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInPompes();
    }

    load(id) {
        this.pompeService.find(id)
            .subscribe((pompeResponse: HttpResponse<Pompe>) => {
                this.pompe = pompeResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInPompes() {
        this.eventSubscriber = this.eventManager.subscribe(
            'pompeListModification',
            (response) => this.load(this.pompe.id)
        );
    }
}
