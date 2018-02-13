import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { EntreeCiterne } from './entree-citerne.model';
import { EntreeCiterneService } from './entree-citerne.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-entree-citerne',
    templateUrl: './entree-citerne.component.html'
})
export class EntreeCiterneComponent implements OnInit, OnDestroy {
entreeCiternes: EntreeCiterne[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private entreeCiterneService: EntreeCiterneService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.entreeCiterneService.query().subscribe(
            (res: HttpResponse<EntreeCiterne[]>) => {
                this.entreeCiternes = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInEntreeCiternes();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: EntreeCiterne) {
        return item.id;
    }
    registerChangeInEntreeCiternes() {
        this.eventSubscriber = this.eventManager.subscribe('entreeCiterneListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
