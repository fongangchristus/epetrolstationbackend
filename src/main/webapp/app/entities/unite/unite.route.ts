import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { UniteComponent } from './unite.component';
import { UniteDetailComponent } from './unite-detail.component';
import { UnitePopupComponent } from './unite-dialog.component';
import { UniteDeletePopupComponent } from './unite-delete-dialog.component';

export const uniteRoute: Routes = [
    {
        path: 'unite',
        component: UniteComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'epetrolstationbackendApp.unite.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'unite/:id',
        component: UniteDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'epetrolstationbackendApp.unite.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const unitePopupRoute: Routes = [
    {
        path: 'unite-new',
        component: UnitePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'epetrolstationbackendApp.unite.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'unite/:id/edit',
        component: UnitePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'epetrolstationbackendApp.unite.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'unite/:id/delete',
        component: UniteDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'epetrolstationbackendApp.unite.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
