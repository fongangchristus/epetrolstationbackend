import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EpetrolstationbackendSharedModule } from '../../shared';
import {
    EntreeCarburantService,
    EntreeCarburantPopupService,
    EntreeCarburantComponent,
    EntreeCarburantDetailComponent,
    EntreeCarburantDialogComponent,
    EntreeCarburantPopupComponent,
    EntreeCarburantDeletePopupComponent,
    EntreeCarburantDeleteDialogComponent,
    entreeCarburantRoute,
    entreeCarburantPopupRoute,
} from './';

const ENTITY_STATES = [
    ...entreeCarburantRoute,
    ...entreeCarburantPopupRoute,
];

@NgModule({
    imports: [
        EpetrolstationbackendSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        EntreeCarburantComponent,
        EntreeCarburantDetailComponent,
        EntreeCarburantDialogComponent,
        EntreeCarburantDeleteDialogComponent,
        EntreeCarburantPopupComponent,
        EntreeCarburantDeletePopupComponent,
    ],
    entryComponents: [
        EntreeCarburantComponent,
        EntreeCarburantDialogComponent,
        EntreeCarburantPopupComponent,
        EntreeCarburantDeleteDialogComponent,
        EntreeCarburantDeletePopupComponent,
    ],
    providers: [
        EntreeCarburantService,
        EntreeCarburantPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EpetrolstationbackendEntreeCarburantModule {}
