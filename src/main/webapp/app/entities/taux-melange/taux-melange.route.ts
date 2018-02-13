import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { TauxMelangeComponent } from './taux-melange.component';
import { TauxMelangeDetailComponent } from './taux-melange-detail.component';
import { TauxMelangePopupComponent } from './taux-melange-dialog.component';
import { TauxMelangeDeletePopupComponent } from './taux-melange-delete-dialog.component';

export const tauxMelangeRoute: Routes = [
    {
        path: 'taux-melange',
        component: TauxMelangeComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'epetrolstationbackendApp.tauxMelange.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'taux-melange/:id',
        component: TauxMelangeDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'epetrolstationbackendApp.tauxMelange.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const tauxMelangePopupRoute: Routes = [
    {
        path: 'taux-melange-new',
        component: TauxMelangePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'epetrolstationbackendApp.tauxMelange.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'taux-melange/:id/edit',
        component: TauxMelangePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'epetrolstationbackendApp.tauxMelange.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'taux-melange/:id/delete',
        component: TauxMelangeDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'epetrolstationbackendApp.tauxMelange.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
