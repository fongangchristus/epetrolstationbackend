import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { TypeTresorerie } from './type-tresorerie.model';
import { TypeTresorerieService } from './type-tresorerie.service';

@Component({
    selector: 'jhi-type-tresorerie-detail',
    templateUrl: './type-tresorerie-detail.component.html'
})
export class TypeTresorerieDetailComponent implements OnInit, OnDestroy {

    typeTresorerie: TypeTresorerie;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private typeTresorerieService: TypeTresorerieService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInTypeTresoreries();
    }

    load(id) {
        this.typeTresorerieService.find(id)
            .subscribe((typeTresorerieResponse: HttpResponse<TypeTresorerie>) => {
                this.typeTresorerie = typeTresorerieResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInTypeTresoreries() {
        this.eventSubscriber = this.eventManager.subscribe(
            'typeTresorerieListModification',
            (response) => this.load(this.typeTresorerie.id)
        );
    }
}
