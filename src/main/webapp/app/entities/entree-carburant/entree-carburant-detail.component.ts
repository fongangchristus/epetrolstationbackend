import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { EntreeCarburant } from './entree-carburant.model';
import { EntreeCarburantService } from './entree-carburant.service';

@Component({
    selector: 'jhi-entree-carburant-detail',
    templateUrl: './entree-carburant-detail.component.html'
})
export class EntreeCarburantDetailComponent implements OnInit, OnDestroy {

    entreeCarburant: EntreeCarburant;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private entreeCarburantService: EntreeCarburantService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInEntreeCarburants();
    }

    load(id) {
        this.entreeCarburantService.find(id)
            .subscribe((entreeCarburantResponse: HttpResponse<EntreeCarburant>) => {
                this.entreeCarburant = entreeCarburantResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInEntreeCarburants() {
        this.eventSubscriber = this.eventManager.subscribe(
            'entreeCarburantListModification',
            (response) => this.load(this.entreeCarburant.id)
        );
    }
}
