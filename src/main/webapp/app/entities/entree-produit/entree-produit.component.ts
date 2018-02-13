import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { EntreeProduit } from './entree-produit.model';
import { EntreeProduitService } from './entree-produit.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-entree-produit',
    templateUrl: './entree-produit.component.html'
})
export class EntreeProduitComponent implements OnInit, OnDestroy {
entreeProduits: EntreeProduit[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private entreeProduitService: EntreeProduitService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.entreeProduitService.query().subscribe(
            (res: HttpResponse<EntreeProduit[]>) => {
                this.entreeProduits = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInEntreeProduits();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: EntreeProduit) {
        return item.id;
    }
    registerChangeInEntreeProduits() {
        this.eventSubscriber = this.eventManager.subscribe('entreeProduitListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
