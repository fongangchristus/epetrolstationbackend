import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { StationComponent } from './station.component';
import { StationDetailComponent } from './station-detail.component';
import { StationPopupComponent } from './station-dialog.component';
import { StationDeletePopupComponent } from './station-delete-dialog.component';

export const stationRoute: Routes = [
    {
        path: 'station',
        component: StationComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'epetrolstationbackendApp.station.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'station/:id',
        component: StationDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'epetrolstationbackendApp.station.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const stationPopupRoute: Routes = [
    {
        path: 'station-new',
        component: StationPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'epetrolstationbackendApp.station.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'station/:id/edit',
        component: StationPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'epetrolstationbackendApp.station.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'station/:id/delete',
        component: StationDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'epetrolstationbackendApp.station.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
