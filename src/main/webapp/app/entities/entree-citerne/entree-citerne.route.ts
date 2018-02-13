import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { EntreeCiterneComponent } from './entree-citerne.component';
import { EntreeCiterneDetailComponent } from './entree-citerne-detail.component';
import { EntreeCiternePopupComponent } from './entree-citerne-dialog.component';
import { EntreeCiterneDeletePopupComponent } from './entree-citerne-delete-dialog.component';

export const entreeCiterneRoute: Routes = [
    {
        path: 'entree-citerne',
        component: EntreeCiterneComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'epetrolstationbackendApp.entreeCiterne.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'entree-citerne/:id',
        component: EntreeCiterneDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'epetrolstationbackendApp.entreeCiterne.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const entreeCiternePopupRoute: Routes = [
    {
        path: 'entree-citerne-new',
        component: EntreeCiternePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'epetrolstationbackendApp.entreeCiterne.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'entree-citerne/:id/edit',
        component: EntreeCiternePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'epetrolstationbackendApp.entreeCiterne.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'entree-citerne/:id/delete',
        component: EntreeCiterneDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'epetrolstationbackendApp.entreeCiterne.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
