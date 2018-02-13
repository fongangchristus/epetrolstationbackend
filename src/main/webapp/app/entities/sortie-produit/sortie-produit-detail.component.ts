import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { SortieProduit } from './sortie-produit.model';
import { SortieProduitService } from './sortie-produit.service';

@Component({
    selector: 'jhi-sortie-produit-detail',
    templateUrl: './sortie-produit-detail.component.html'
})
export class SortieProduitDetailComponent implements OnInit, OnDestroy {

    sortieProduit: SortieProduit;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private sortieProduitService: SortieProduitService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInSortieProduits();
    }

    load(id) {
        this.sortieProduitService.find(id)
            .subscribe((sortieProduitResponse: HttpResponse<SortieProduit>) => {
                this.sortieProduit = sortieProduitResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInSortieProduits() {
        this.eventSubscriber = this.eventManager.subscribe(
            'sortieProduitListModification',
            (response) => this.load(this.sortieProduit.id)
        );
    }
}
