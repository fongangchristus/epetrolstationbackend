import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { CatCarburant } from './cat-carburant.model';
import { CatCarburantService } from './cat-carburant.service';

@Component({
    selector: 'jhi-cat-carburant-detail',
    templateUrl: './cat-carburant-detail.component.html'
})
export class CatCarburantDetailComponent implements OnInit, OnDestroy {

    catCarburant: CatCarburant;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private catCarburantService: CatCarburantService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInCatCarburants();
    }

    load(id) {
        this.catCarburantService.find(id)
            .subscribe((catCarburantResponse: HttpResponse<CatCarburant>) => {
                this.catCarburant = catCarburantResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInCatCarburants() {
        this.eventSubscriber = this.eventManager.subscribe(
            'catCarburantListModification',
            (response) => this.load(this.catCarburant.id)
        );
    }
}
