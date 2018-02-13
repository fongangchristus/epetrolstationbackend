import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { EntreeProduit } from './entree-produit.model';
import { EntreeProduitService } from './entree-produit.service';

@Component({
    selector: 'jhi-entree-produit-detail',
    templateUrl: './entree-produit-detail.component.html'
})
export class EntreeProduitDetailComponent implements OnInit, OnDestroy {

    entreeProduit: EntreeProduit;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private entreeProduitService: EntreeProduitService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInEntreeProduits();
    }

    load(id) {
        this.entreeProduitService.find(id)
            .subscribe((entreeProduitResponse: HttpResponse<EntreeProduit>) => {
                this.entreeProduit = entreeProduitResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInEntreeProduits() {
        this.eventSubscriber = this.eventManager.subscribe(
            'entreeProduitListModification',
            (response) => this.load(this.entreeProduit.id)
        );
    }
}
