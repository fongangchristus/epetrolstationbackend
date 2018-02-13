import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { CategorieDepense } from './categorie-depense.model';
import { CategorieDepenseService } from './categorie-depense.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-categorie-depense',
    templateUrl: './categorie-depense.component.html'
})
export class CategorieDepenseComponent implements OnInit, OnDestroy {
categorieDepenses: CategorieDepense[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private categorieDepenseService: CategorieDepenseService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.categorieDepenseService.query().subscribe(
            (res: HttpResponse<CategorieDepense[]>) => {
                this.categorieDepenses = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInCategorieDepenses();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: CategorieDepense) {
        return item.id;
    }
    registerChangeInCategorieDepenses() {
        this.eventSubscriber = this.eventManager.subscribe('categorieDepenseListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
