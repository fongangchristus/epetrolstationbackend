import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PompeComponent } from './pompe.component';
import { PompeDetailComponent } from './pompe-detail.component';
import { PompePopupComponent } from './pompe-dialog.component';
import { PompeDeletePopupComponent } from './pompe-delete-dialog.component';

export const pompeRoute: Routes = [
    {
        path: 'pompe',
        component: PompeComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'epetrolstationbackendApp.pompe.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'pompe/:id',
        component: PompeDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'epetrolstationbackendApp.pompe.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const pompePopupRoute: Routes = [
    {
        path: 'pompe-new',
        component: PompePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'epetrolstationbackendApp.pompe.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'pompe/:id/edit',
        component: PompePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'epetrolstationbackendApp.pompe.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'pompe/:id/delete',
        component: PompeDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'epetrolstationbackendApp.pompe.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
