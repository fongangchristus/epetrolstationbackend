import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Unite } from './unite.model';
import { UniteService } from './unite.service';

@Component({
    selector: 'jhi-unite-detail',
    templateUrl: './unite-detail.component.html'
})
export class UniteDetailComponent implements OnInit, OnDestroy {

    unite: Unite;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private uniteService: UniteService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInUnites();
    }

    load(id) {
        this.uniteService.find(id)
            .subscribe((uniteResponse: HttpResponse<Unite>) => {
                this.unite = uniteResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInUnites() {
        this.eventSubscriber = this.eventManager.subscribe(
            'uniteListModification',
            (response) => this.load(this.unite.id)
        );
    }
}
