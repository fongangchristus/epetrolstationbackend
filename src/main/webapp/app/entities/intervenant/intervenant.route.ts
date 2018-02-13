import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { IntervenantComponent } from './intervenant.component';
import { IntervenantDetailComponent } from './intervenant-detail.component';
import { IntervenantPopupComponent } from './intervenant-dialog.component';
import { IntervenantDeletePopupComponent } from './intervenant-delete-dialog.component';

export const intervenantRoute: Routes = [
    {
        path: 'intervenant',
        component: IntervenantComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'epetrolstationbackendApp.intervenant.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'intervenant/:id',
        component: IntervenantDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'epetrolstationbackendApp.intervenant.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const intervenantPopupRoute: Routes = [
    {
        path: 'intervenant-new',
        component: IntervenantPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'epetrolstationbackendApp.intervenant.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'intervenant/:id/edit',
        component: IntervenantPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'epetrolstationbackendApp.intervenant.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'intervenant/:id/delete',
        component: IntervenantDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'epetrolstationbackendApp.intervenant.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
