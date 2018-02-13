import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { SortieProduit } from './sortie-produit.model';
import { SortieProduitService } from './sortie-produit.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-sortie-produit',
    templateUrl: './sortie-produit.component.html'
})
export class SortieProduitComponent implements OnInit, OnDestroy {
sortieProduits: SortieProduit[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private sortieProduitService: SortieProduitService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.sortieProduitService.query().subscribe(
            (res: HttpResponse<SortieProduit[]>) => {
                this.sortieProduits = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInSortieProduits();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: SortieProduit) {
        return item.id;
    }
    registerChangeInSortieProduits() {
        this.eventSubscriber = this.eventManager.subscribe('sortieProduitListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
