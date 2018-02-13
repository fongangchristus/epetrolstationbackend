import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Tresorerie } from './tresorerie.model';
import { TresorerieService } from './tresorerie.service';

@Component({
    selector: 'jhi-tresorerie-detail',
    templateUrl: './tresorerie-detail.component.html'
})
export class TresorerieDetailComponent implements OnInit, OnDestroy {

    tresorerie: Tresorerie;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private tresorerieService: TresorerieService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInTresoreries();
    }

    load(id) {
        this.tresorerieService.find(id)
            .subscribe((tresorerieResponse: HttpResponse<Tresorerie>) => {
                this.tresorerie = tresorerieResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInTresoreries() {
        this.eventSubscriber = this.eventManager.subscribe(
            'tresorerieListModification',
            (response) => this.load(this.tresorerie.id)
        );
    }
}
