import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { EntreeCiterne } from './entree-citerne.model';
import { EntreeCiterneService } from './entree-citerne.service';

@Component({
    selector: 'jhi-entree-citerne-detail',
    templateUrl: './entree-citerne-detail.component.html'
})
export class EntreeCiterneDetailComponent implements OnInit, OnDestroy {

    entreeCiterne: EntreeCiterne;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private entreeCiterneService: EntreeCiterneService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInEntreeCiternes();
    }

    load(id) {
        this.entreeCiterneService.find(id)
            .subscribe((entreeCiterneResponse: HttpResponse<EntreeCiterne>) => {
                this.entreeCiterne = entreeCiterneResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInEntreeCiternes() {
        this.eventSubscriber = this.eventManager.subscribe(
            'entreeCiterneListModification',
            (response) => this.load(this.entreeCiterne.id)
        );
    }
}
