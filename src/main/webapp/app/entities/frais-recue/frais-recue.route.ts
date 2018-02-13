import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { FraisRecueComponent } from './frais-recue.component';
import { FraisRecueDetailComponent } from './frais-recue-detail.component';
import { FraisRecuePopupComponent } from './frais-recue-dialog.component';
import { FraisRecueDeletePopupComponent } from './frais-recue-delete-dialog.component';

export const fraisRecueRoute: Routes = [
    {
        path: 'frais-recue',
        component: FraisRecueComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'epetrolstationbackendApp.fraisRecue.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'frais-recue/:id',
        component: FraisRecueDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'epetrolstationbackendApp.fraisRecue.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const fraisRecuePopupRoute: Routes = [
    {
        path: 'frais-recue-new',
        component: FraisRecuePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'epetrolstationbackendApp.fraisRecue.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'frais-recue/:id/edit',
        component: FraisRecuePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'epetrolstationbackendApp.fraisRecue.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'frais-recue/:id/delete',
        component: FraisRecueDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'epetrolstationbackendApp.fraisRecue.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
