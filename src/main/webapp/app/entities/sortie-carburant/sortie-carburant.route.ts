import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { SortieCarburantComponent } from './sortie-carburant.component';
import { SortieCarburantDetailComponent } from './sortie-carburant-detail.component';
import { SortieCarburantPopupComponent } from './sortie-carburant-dialog.component';
import { SortieCarburantDeletePopupComponent } from './sortie-carburant-delete-dialog.component';

export const sortieCarburantRoute: Routes = [
    {
        path: 'sortie-carburant',
        component: SortieCarburantComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'epetrolstationbackendApp.sortieCarburant.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'sortie-carburant/:id',
        component: SortieCarburantDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'epetrolstationbackendApp.sortieCarburant.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const sortieCarburantPopupRoute: Routes = [
    {
        path: 'sortie-carburant-new',
        component: SortieCarburantPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'epetrolstationbackendApp.sortieCarburant.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'sortie-carburant/:id/edit',
        component: SortieCarburantPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'epetrolstationbackendApp.sortieCarburant.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'sortie-carburant/:id/delete',
        component: SortieCarburantDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'epetrolstationbackendApp.sortieCarburant.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
