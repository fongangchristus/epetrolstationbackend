import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { MyserviceComponent } from './myservice.component';
import { MyserviceDetailComponent } from './myservice-detail.component';
import { MyservicePopupComponent } from './myservice-dialog.component';
import { MyserviceDeletePopupComponent } from './myservice-delete-dialog.component';

export const myserviceRoute: Routes = [
    {
        path: 'myservice',
        component: MyserviceComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'epetrolstationbackendApp.myservice.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'myservice/:id',
        component: MyserviceDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'epetrolstationbackendApp.myservice.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const myservicePopupRoute: Routes = [
    {
        path: 'myservice-new',
        component: MyservicePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'epetrolstationbackendApp.myservice.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'myservice/:id/edit',
        component: MyservicePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'epetrolstationbackendApp.myservice.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'myservice/:id/delete',
        component: MyserviceDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'epetrolstationbackendApp.myservice.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
