import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { CiterneComponent } from './citerne.component';
import { CiterneDetailComponent } from './citerne-detail.component';
import { CiternePopupComponent } from './citerne-dialog.component';
import { CiterneDeletePopupComponent } from './citerne-delete-dialog.component';

export const citerneRoute: Routes = [
    {
        path: 'citerne',
        component: CiterneComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'epetrolstationbackendApp.citerne.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'citerne/:id',
        component: CiterneDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'epetrolstationbackendApp.citerne.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const citernePopupRoute: Routes = [
    {
        path: 'citerne-new',
        component: CiternePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'epetrolstationbackendApp.citerne.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'citerne/:id/edit',
        component: CiternePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'epetrolstationbackendApp.citerne.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'citerne/:id/delete',
        component: CiterneDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'epetrolstationbackendApp.citerne.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
