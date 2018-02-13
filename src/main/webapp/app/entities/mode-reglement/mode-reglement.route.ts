import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { ModeReglementComponent } from './mode-reglement.component';
import { ModeReglementDetailComponent } from './mode-reglement-detail.component';
import { ModeReglementPopupComponent } from './mode-reglement-dialog.component';
import { ModeReglementDeletePopupComponent } from './mode-reglement-delete-dialog.component';

export const modeReglementRoute: Routes = [
    {
        path: 'mode-reglement',
        component: ModeReglementComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'epetrolstationbackendApp.modeReglement.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'mode-reglement/:id',
        component: ModeReglementDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'epetrolstationbackendApp.modeReglement.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const modeReglementPopupRoute: Routes = [
    {
        path: 'mode-reglement-new',
        component: ModeReglementPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'epetrolstationbackendApp.modeReglement.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'mode-reglement/:id/edit',
        component: ModeReglementPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'epetrolstationbackendApp.modeReglement.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'mode-reglement/:id/delete',
        component: ModeReglementDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'epetrolstationbackendApp.modeReglement.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
