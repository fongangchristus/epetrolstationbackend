import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { CatCarburantComponent } from './cat-carburant.component';
import { CatCarburantDetailComponent } from './cat-carburant-detail.component';
import { CatCarburantPopupComponent } from './cat-carburant-dialog.component';
import { CatCarburantDeletePopupComponent } from './cat-carburant-delete-dialog.component';

export const catCarburantRoute: Routes = [
    {
        path: 'cat-carburant',
        component: CatCarburantComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'epetrolstationbackendApp.catCarburant.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'cat-carburant/:id',
        component: CatCarburantDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'epetrolstationbackendApp.catCarburant.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const catCarburantPopupRoute: Routes = [
    {
        path: 'cat-carburant-new',
        component: CatCarburantPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'epetrolstationbackendApp.catCarburant.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'cat-carburant/:id/edit',
        component: CatCarburantPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'epetrolstationbackendApp.catCarburant.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'cat-carburant/:id/delete',
        component: CatCarburantDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'epetrolstationbackendApp.catCarburant.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
