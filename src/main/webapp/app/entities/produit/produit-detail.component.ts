import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Produit } from './produit.model';
import { ProduitService } from './produit.service';

@Component({
    selector: 'jhi-produit-detail',
    templateUrl: './produit-detail.component.html'
})
export class ProduitDetailComponent implements OnInit, OnDestroy {

    produit: Produit;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private produitService: ProduitService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInProduits();
    }

    load(id) {
        this.produitService.find(id)
            .subscribe((produitResponse: HttpResponse<Produit>) => {
                this.produit = produitResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInProduits() {
        this.eventSubscriber = this.eventManager.subscribe(
            'produitListModification',
            (response) => this.load(this.produit.id)
        );
    }
}
