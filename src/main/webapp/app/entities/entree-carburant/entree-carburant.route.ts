import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { EntreeCarburantComponent } from './entree-carburant.component';
import { EntreeCarburantDetailComponent } from './entree-carburant-detail.component';
import { EntreeCarburantPopupComponent } from './entree-carburant-dialog.component';
import { EntreeCarburantDeletePopupComponent } from './entree-carburant-delete-dialog.component';

export const entreeCarburantRoute: Routes = [
    {
        path: 'entree-carburant',
        component: EntreeCarburantComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'epetrolstationbackendApp.entreeCarburant.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'entree-carburant/:id',
        component: EntreeCarburantDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'epetrolstationbackendApp.entreeCarburant.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const entreeCarburantPopupRoute: Routes = [
    {
        path: 'entree-carburant-new',
        component: EntreeCarburantPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'epetrolstationbackendApp.entreeCarburant.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'entree-carburant/:id/edit',
        component: EntreeCarburantPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'epetrolstationbackendApp.entreeCarburant.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'entree-carburant/:id/delete',
        component: EntreeCarburantDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'epetrolstationbackendApp.entreeCarburant.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
