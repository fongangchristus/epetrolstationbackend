import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EpetrolstationbackendSharedModule } from '../../shared';
import { EpetrolstationbackendAdminModule } from '../../admin/admin.module';
import {
    IntervenantService,
    IntervenantPopupService,
    IntervenantComponent,
    IntervenantDetailComponent,
    IntervenantDialogComponent,
    IntervenantPopupComponent,
    IntervenantDeletePopupComponent,
    IntervenantDeleteDialogComponent,
    intervenantRoute,
    intervenantPopupRoute,
} from './';

const ENTITY_STATES = [
    ...intervenantRoute,
    ...intervenantPopupRoute,
];

@NgModule({
    imports: [
        EpetrolstationbackendSharedModule,
        EpetrolstationbackendAdminModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        IntervenantComponent,
        IntervenantDetailComponent,
        IntervenantDialogComponent,
        IntervenantDeleteDialogComponent,
        IntervenantPopupComponent,
        IntervenantDeletePopupComponent,
    ],
    entryComponents: [
        IntervenantComponent,
        IntervenantDialogComponent,
        IntervenantPopupComponent,
        IntervenantDeleteDialogComponent,
        IntervenantDeletePopupComponent,
    ],
    providers: [
        IntervenantService,
        IntervenantPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EpetrolstationbackendIntervenantModule {}
