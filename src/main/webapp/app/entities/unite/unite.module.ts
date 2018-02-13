import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EpetrolstationbackendSharedModule } from '../../shared';
import {
    UniteService,
    UnitePopupService,
    UniteComponent,
    UniteDetailComponent,
    UniteDialogComponent,
    UnitePopupComponent,
    UniteDeletePopupComponent,
    UniteDeleteDialogComponent,
    uniteRoute,
    unitePopupRoute,
} from './';

const ENTITY_STATES = [
    ...uniteRoute,
    ...unitePopupRoute,
];

@NgModule({
    imports: [
        EpetrolstationbackendSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        UniteComponent,
        UniteDetailComponent,
        UniteDialogComponent,
        UniteDeleteDialogComponent,
        UnitePopupComponent,
        UniteDeletePopupComponent,
    ],
    entryComponents: [
        UniteComponent,
        UniteDialogComponent,
        UnitePopupComponent,
        UniteDeleteDialogComponent,
        UniteDeletePopupComponent,
    ],
    providers: [
        UniteService,
        UnitePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EpetrolstationbackendUniteModule {}
