import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Citerne } from './citerne.model';
import { CiterneService } from './citerne.service';

@Component({
    selector: 'jhi-citerne-detail',
    templateUrl: './citerne-detail.component.html'
})
export class CiterneDetailComponent implements OnInit, OnDestroy {

    citerne: Citerne;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private citerneService: CiterneService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInCiternes();
    }

    load(id) {
        this.citerneService.find(id)
            .subscribe((citerneResponse: HttpResponse<Citerne>) => {
                this.citerne = citerneResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInCiternes() {
        this.eventSubscriber = this.eventManager.subscribe(
            'citerneListModification',
            (response) => this.load(this.citerne.id)
        );
    }
}
