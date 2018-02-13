import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { TypeIntervenantComponent } from './type-intervenant.component';
import { TypeIntervenantDetailComponent } from './type-intervenant-detail.component';
import { TypeIntervenantPopupComponent } from './type-intervenant-dialog.component';
import { TypeIntervenantDeletePopupComponent } from './type-intervenant-delete-dialog.component';

export const typeIntervenantRoute: Routes = [
    {
        path: 'type-intervenant',
        component: TypeIntervenantComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'epetrolstationbackendApp.typeIntervenant.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'type-intervenant/:id',
        component: TypeIntervenantDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'epetrolstationbackendApp.typeIntervenant.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const typeIntervenantPopupRoute: Routes = [
    {
        path: 'type-intervenant-new',
        component: TypeIntervenantPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'epetrolstationbackendApp.typeIntervenant.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'type-intervenant/:id/edit',
        component: TypeIntervenantPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'epetrolstationbackendApp.typeIntervenant.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'type-intervenant/:id/delete',
        component: TypeIntervenantDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'epetrolstationbackendApp.typeIntervenant.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
