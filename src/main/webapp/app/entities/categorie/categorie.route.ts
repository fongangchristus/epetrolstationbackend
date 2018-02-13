import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { CategorieComponent } from './categorie.component';
import { CategorieDetailComponent } from './categorie-detail.component';
import { CategoriePopupComponent } from './categorie-dialog.component';
import { CategorieDeletePopupComponent } from './categorie-delete-dialog.component';

export const categorieRoute: Routes = [
    {
        path: 'categorie',
        component: CategorieComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'epetrolstationbackendApp.categorie.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'categorie/:id',
        component: CategorieDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'epetrolstationbackendApp.categorie.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const categoriePopupRoute: Routes = [
    {
        path: 'categorie-new',
        component: CategoriePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'epetrolstationbackendApp.categorie.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'categorie/:id/edit',
        component: CategoriePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'epetrolstationbackendApp.categorie.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'categorie/:id/delete',
        component: CategorieDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'epetrolstationbackendApp.categorie.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
