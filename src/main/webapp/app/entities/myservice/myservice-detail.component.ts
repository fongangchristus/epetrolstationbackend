import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Myservice } from './myservice.model';
import { MyserviceService } from './myservice.service';

@Component({
    selector: 'jhi-myservice-detail',
    templateUrl: './myservice-detail.component.html'
})
export class MyserviceDetailComponent implements OnInit, OnDestroy {

    myservice: Myservice;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private myserviceService: MyserviceService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInMyservices();
    }

    load(id) {
        this.myserviceService.find(id)
            .subscribe((myserviceResponse: HttpResponse<Myservice>) => {
                this.myservice = myserviceResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInMyservices() {
        this.eventSubscriber = this.eventManager.subscribe(
            'myserviceListModification',
            (response) => this.load(this.myservice.id)
        );
    }
}
