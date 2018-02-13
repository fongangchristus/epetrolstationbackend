import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { TypeIntervenant } from './type-intervenant.model';
import { TypeIntervenantService } from './type-intervenant.service';

@Component({
    selector: 'jhi-type-intervenant-detail',
    templateUrl: './type-intervenant-detail.component.html'
})
export class TypeIntervenantDetailComponent implements OnInit, OnDestroy {

    typeIntervenant: TypeIntervenant;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private typeIntervenantService: TypeIntervenantService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInTypeIntervenants();
    }

    load(id) {
        this.typeIntervenantService.find(id)
            .subscribe((typeIntervenantResponse: HttpResponse<TypeIntervenant>) => {
                this.typeIntervenant = typeIntervenantResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInTypeIntervenants() {
        this.eventSubscriber = this.eventManager.subscribe(
            'typeIntervenantListModification',
            (response) => this.load(this.typeIntervenant.id)
        );
    }
}
