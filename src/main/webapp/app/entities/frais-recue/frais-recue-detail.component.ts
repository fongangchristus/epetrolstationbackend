import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { FraisRecue } from './frais-recue.model';
import { FraisRecueService } from './frais-recue.service';

@Component({
    selector: 'jhi-frais-recue-detail',
    templateUrl: './frais-recue-detail.component.html'
})
export class FraisRecueDetailComponent implements OnInit, OnDestroy {

    fraisRecue: FraisRecue;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private fraisRecueService: FraisRecueService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInFraisRecues();
    }

    load(id) {
        this.fraisRecueService.find(id)
            .subscribe((fraisRecueResponse: HttpResponse<FraisRecue>) => {
                this.fraisRecue = fraisRecueResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInFraisRecues() {
        this.eventSubscriber = this.eventManager.subscribe(
            'fraisRecueListModification',
            (response) => this.load(this.fraisRecue.id)
        );
    }
}
