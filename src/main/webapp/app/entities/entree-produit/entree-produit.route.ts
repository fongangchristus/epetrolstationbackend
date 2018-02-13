import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { EntreeProduitComponent } from './entree-produit.component';
import { EntreeProduitDetailComponent } from './entree-produit-detail.component';
import { EntreeProduitPopupComponent } from './entree-produit-dialog.component';
import { EntreeProduitDeletePopupComponent } from './entree-produit-delete-dialog.component';

export const entreeProduitRoute: Routes = [
    {
        path: 'entree-produit',
        component: EntreeProduitComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'epetrolstationbackendApp.entreeProduit.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'entree-produit/:id',
        component: EntreeProduitDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'epetrolstationbackendApp.entreeProduit.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const entreeProduitPopupRoute: Routes = [
    {
        path: 'entree-produit-new',
        component: EntreeProduitPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'epetrolstationbackendApp.entreeProduit.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'entree-produit/:id/edit',
        component: EntreeProduitPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'epetrolstationbackendApp.entreeProduit.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'entree-produit/:id/delete',
        component: EntreeProduitDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'epetrolstationbackendApp.entreeProduit.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
