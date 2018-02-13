import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { TypeTresorerieComponent } from './type-tresorerie.component';
import { TypeTresorerieDetailComponent } from './type-tresorerie-detail.component';
import { TypeTresoreriePopupComponent } from './type-tresorerie-dialog.component';
import { TypeTresorerieDeletePopupComponent } from './type-tresorerie-delete-dialog.component';

export const typeTresorerieRoute: Routes = [
    {
        path: 'type-tresorerie',
        component: TypeTresorerieComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'epetrolstationbackendApp.typeTresorerie.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'type-tresorerie/:id',
        component: TypeTresorerieDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'epetrolstationbackendApp.typeTresorerie.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const typeTresoreriePopupRoute: Routes = [
    {
        path: 'type-tresorerie-new',
        component: TypeTresoreriePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'epetrolstationbackendApp.typeTresorerie.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'type-tresorerie/:id/edit',
        component: TypeTresoreriePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'epetrolstationbackendApp.typeTresorerie.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'type-tresorerie/:id/delete',
        component: TypeTresorerieDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'epetrolstationbackendApp.typeTresorerie.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
