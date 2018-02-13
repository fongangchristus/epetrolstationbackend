import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { CategorieDepenseComponent } from './categorie-depense.component';
import { CategorieDepenseDetailComponent } from './categorie-depense-detail.component';
import { CategorieDepensePopupComponent } from './categorie-depense-dialog.component';
import { CategorieDepenseDeletePopupComponent } from './categorie-depense-delete-dialog.component';

export const categorieDepenseRoute: Routes = [
    {
        path: 'categorie-depense',
        component: CategorieDepenseComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'epetrolstationbackendApp.categorieDepense.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'categorie-depense/:id',
        component: CategorieDepenseDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'epetrolstationbackendApp.categorieDepense.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const categorieDepensePopupRoute: Routes = [
    {
        path: 'categorie-depense-new',
        component: CategorieDepensePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'epetrolstationbackendApp.categorieDepense.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'categorie-depense/:id/edit',
        component: CategorieDepensePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'epetrolstationbackendApp.categorieDepense.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'categorie-depense/:id/delete',
        component: CategorieDepenseDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'epetrolstationbackendApp.categorieDepense.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
