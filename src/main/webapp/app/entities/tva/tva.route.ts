import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { TvaComponent } from './tva.component';
import { TvaDetailComponent } from './tva-detail.component';
import { TvaPopupComponent } from './tva-dialog.component';
import { TvaDeletePopupComponent } from './tva-delete-dialog.component';

export const tvaRoute: Routes = [
    {
        path: 'tva',
        component: TvaComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'epetrolstationbackendApp.tva.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'tva/:id',
        component: TvaDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'epetrolstationbackendApp.tva.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const tvaPopupRoute: Routes = [
    {
        path: 'tva-new',
        component: TvaPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'epetrolstationbackendApp.tva.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'tva/:id/edit',
        component: TvaPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'epetrolstationbackendApp.tva.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'tva/:id/delete',
        component: TvaDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'epetrolstationbackendApp.tva.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
