import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { SortieMyserviceComponent } from './sortie-myservice.component';
import { SortieMyserviceDetailComponent } from './sortie-myservice-detail.component';
import { SortieMyservicePopupComponent } from './sortie-myservice-dialog.component';
import { SortieMyserviceDeletePopupComponent } from './sortie-myservice-delete-dialog.component';

export const sortieMyserviceRoute: Routes = [
    {
        path: 'sortie-myservice',
        component: SortieMyserviceComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'epetrolstationbackendApp.sortieMyservice.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'sortie-myservice/:id',
        component: SortieMyserviceDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'epetrolstationbackendApp.sortieMyservice.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const sortieMyservicePopupRoute: Routes = [
    {
        path: 'sortie-myservice-new',
        component: SortieMyservicePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'epetrolstationbackendApp.sortieMyservice.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'sortie-myservice/:id/edit',
        component: SortieMyservicePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'epetrolstationbackendApp.sortieMyservice.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'sortie-myservice/:id/delete',
        component: SortieMyserviceDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'epetrolstationbackendApp.sortieMyservice.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
