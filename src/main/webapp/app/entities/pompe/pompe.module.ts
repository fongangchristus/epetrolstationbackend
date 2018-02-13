import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EpetrolstationbackendSharedModule } from '../../shared';
import {
    PompeService,
    PompePopupService,
    PompeComponent,
    PompeDetailComponent,
    PompeDialogComponent,
    PompePopupComponent,
    PompeDeletePopupComponent,
    PompeDeleteDialogComponent,
    pompeRoute,
    pompePopupRoute,
} from './';

const ENTITY_STATES = [
    ...pompeRoute,
    ...pompePopupRoute,
];

@NgModule({
    imports: [
        EpetrolstationbackendSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        PompeComponent,
        PompeDetailComponent,
        PompeDialogComponent,
        PompeDeleteDialogComponent,
        PompePopupComponent,
        PompeDeletePopupComponent,
    ],
    entryComponents: [
        PompeComponent,
        PompeDialogComponent,
        PompePopupComponent,
        PompeDeleteDialogComponent,
        PompeDeletePopupComponent,
    ],
    providers: [
        PompeService,
        PompePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EpetrolstationbackendPompeModule {}
