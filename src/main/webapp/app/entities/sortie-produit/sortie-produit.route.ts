import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { SortieProduitComponent } from './sortie-produit.component';
import { SortieProduitDetailComponent } from './sortie-produit-detail.component';
import { SortieProduitPopupComponent } from './sortie-produit-dialog.component';
import { SortieProduitDeletePopupComponent } from './sortie-produit-delete-dialog.component';

export const sortieProduitRoute: Routes = [
    {
        path: 'sortie-produit',
        component: SortieProduitComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'epetrolstationbackendApp.sortieProduit.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'sortie-produit/:id',
        component: SortieProduitDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'epetrolstationbackendApp.sortieProduit.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const sortieProduitPopupRoute: Routes = [
    {
        path: 'sortie-produit-new',
        component: SortieProduitPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'epetrolstationbackendApp.sortieProduit.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'sortie-produit/:id/edit',
        component: SortieProduitPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'epetrolstationbackendApp.sortieProduit.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'sortie-produit/:id/delete',
        component: SortieProduitDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'epetrolstationbackendApp.sortieProduit.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
