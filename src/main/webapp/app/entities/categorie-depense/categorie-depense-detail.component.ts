import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { CategorieDepense } from './categorie-depense.model';
import { CategorieDepenseService } from './categorie-depense.service';

@Component({
    selector: 'jhi-categorie-depense-detail',
    templateUrl: './categorie-depense-detail.component.html'
})
export class CategorieDepenseDetailComponent implements OnInit, OnDestroy {

    categorieDepense: CategorieDepense;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private categorieDepenseService: CategorieDepenseService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInCategorieDepenses();
    }

    load(id) {
        this.categorieDepenseService.find(id)
            .subscribe((categorieDepenseResponse: HttpResponse<CategorieDepense>) => {
                this.categorieDepense = categorieDepenseResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInCategorieDepenses() {
        this.eventSubscriber = this.eventManager.subscribe(
            'categorieDepenseListModification',
            (response) => this.load(this.categorieDepense.id)
        );
    }
}
