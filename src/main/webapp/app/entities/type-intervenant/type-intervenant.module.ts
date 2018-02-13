import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EpetrolstationbackendSharedModule } from '../../shared';
import {
    TypeIntervenantService,
    TypeIntervenantPopupService,
    TypeIntervenantComponent,
    TypeIntervenantDetailComponent,
    TypeIntervenantDialogComponent,
    TypeIntervenantPopupComponent,
    TypeIntervenantDeletePopupComponent,
    TypeIntervenantDeleteDialogComponent,
    typeIntervenantRoute,
    typeIntervenantPopupRoute,
} from './';

const ENTITY_STATES = [
    ...typeIntervenantRoute,
    ...typeIntervenantPopupRoute,
];

@NgModule({
    imports: [
        EpetrolstationbackendSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        TypeIntervenantComponent,
        TypeIntervenantDetailComponent,
        TypeIntervenantDialogComponent,
        TypeIntervenantDeleteDialogComponent,
        TypeIntervenantPopupComponent,
        TypeIntervenantDeletePopupComponent,
    ],
    entryComponents: [
        TypeIntervenantComponent,
        TypeIntervenantDialogComponent,
        TypeIntervenantPopupComponent,
        TypeIntervenantDeleteDialogComponent,
        TypeIntervenantDeletePopupComponent,
    ],
    providers: [
        TypeIntervenantService,
        TypeIntervenantPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EpetrolstationbackendTypeIntervenantModule {}
