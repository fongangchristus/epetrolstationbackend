import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Intervenant } from './intervenant.model';
import { IntervenantService } from './intervenant.service';

@Component({
    selector: 'jhi-intervenant-detail',
    templateUrl: './intervenant-detail.component.html'
})
export class IntervenantDetailComponent implements OnInit, OnDestroy {

    intervenant: Intervenant;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private intervenantService: IntervenantService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInIntervenants();
    }

    load(id) {
        this.intervenantService.find(id)
            .subscribe((intervenantResponse: HttpResponse<Intervenant>) => {
                this.intervenant = intervenantResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInIntervenants() {
        this.eventSubscriber = this.eventManager.subscribe(
            'intervenantListModification',
            (response) => this.load(this.intervenant.id)
        );
    }
}
