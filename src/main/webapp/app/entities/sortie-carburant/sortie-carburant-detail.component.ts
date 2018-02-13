import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { SortieCarburant } from './sortie-carburant.model';
import { SortieCarburantService } from './sortie-carburant.service';

@Component({
    selector: 'jhi-sortie-carburant-detail',
    templateUrl: './sortie-carburant-detail.component.html'
})
export class SortieCarburantDetailComponent implements OnInit, OnDestroy {

    sortieCarburant: SortieCarburant;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private sortieCarburantService: SortieCarburantService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInSortieCarburants();
    }

    load(id) {
        this.sortieCarburantService.find(id)
            .subscribe((sortieCarburantResponse: HttpResponse<SortieCarburant>) => {
                this.sortieCarburant = sortieCarburantResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInSortieCarburants() {
        this.eventSubscriber = this.eventManager.subscribe(
            'sortieCarburantListModification',
            (response) => this.load(this.sortieCarburant.id)
        );
    }
}
