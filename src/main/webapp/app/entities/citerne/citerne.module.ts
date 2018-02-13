import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EpetrolstationbackendSharedModule } from '../../shared';
import {
    CiterneService,
    CiternePopupService,
    CiterneComponent,
    CiterneDetailComponent,
    CiterneDialogComponent,
    CiternePopupComponent,
    CiterneDeletePopupComponent,
    CiterneDeleteDialogComponent,
    citerneRoute,
    citernePopupRoute,
} from './';

const ENTITY_STATES = [
    ...citerneRoute,
    ...citernePopupRoute,
];

@NgModule({
    imports: [
        EpetrolstationbackendSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        CiterneComponent,
        CiterneDetailComponent,
        CiterneDialogComponent,
        CiterneDeleteDialogComponent,
        CiternePopupComponent,
        CiterneDeletePopupComponent,
    ],
    entryComponents: [
        CiterneComponent,
        CiterneDialogComponent,
        CiternePopupComponent,
        CiterneDeleteDialogComponent,
        CiterneDeletePopupComponent,
    ],
    providers: [
        CiterneService,
        CiternePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EpetrolstationbackendCiterneModule {}
