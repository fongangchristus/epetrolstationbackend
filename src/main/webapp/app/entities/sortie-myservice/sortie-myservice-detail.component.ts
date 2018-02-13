import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { SortieMyservice } from './sortie-myservice.model';
import { SortieMyserviceService } from './sortie-myservice.service';

@Component({
    selector: 'jhi-sortie-myservice-detail',
    templateUrl: './sortie-myservice-detail.component.html'
})
export class SortieMyserviceDetailComponent implements OnInit, OnDestroy {

    sortieMyservice: SortieMyservice;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private sortieMyserviceService: SortieMyserviceService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInSortieMyservices();
    }

    load(id) {
        this.sortieMyserviceService.find(id)
            .subscribe((sortieMyserviceResponse: HttpResponse<SortieMyservice>) => {
                this.sortieMyservice = sortieMyserviceResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInSortieMyservices() {
        this.eventSubscriber = this.eventManager.subscribe(
            'sortieMyserviceListModification',
            (response) => this.load(this.sortieMyservice.id)
        );
    }
}
