import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { TresorerieComponent } from './tresorerie.component';
import { TresorerieDetailComponent } from './tresorerie-detail.component';
import { TresoreriePopupComponent } from './tresorerie-dialog.component';
import { TresorerieDeletePopupComponent } from './tresorerie-delete-dialog.component';

export const tresorerieRoute: Routes = [
    {
        path: 'tresorerie',
        component: TresorerieComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'epetrolstationbackendApp.tresorerie.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'tresorerie/:id',
        component: TresorerieDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'epetrolstationbackendApp.tresorerie.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const tresoreriePopupRoute: Routes = [
    {
        path: 'tresorerie-new',
        component: TresoreriePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'epetrolstationbackendApp.tresorerie.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'tresorerie/:id/edit',
        component: TresoreriePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'epetrolstationbackendApp.tresorerie.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'tresorerie/:id/delete',
        component: TresorerieDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'epetrolstationbackendApp.tresorerie.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
