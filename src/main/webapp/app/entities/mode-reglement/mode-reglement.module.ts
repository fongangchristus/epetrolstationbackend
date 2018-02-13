import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EpetrolstationbackendSharedModule } from '../../shared';
import {
    ModeReglementService,
    ModeReglementPopupService,
    ModeReglementComponent,
    ModeReglementDetailComponent,
    ModeReglementDialogComponent,
    ModeReglementPopupComponent,
    ModeReglementDeletePopupComponent,
    ModeReglementDeleteDialogComponent,
    modeReglementRoute,
    modeReglementPopupRoute,
} from './';

const ENTITY_STATES = [
    ...modeReglementRoute,
    ...modeReglementPopupRoute,
];

@NgModule({
    imports: [
        EpetrolstationbackendSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        ModeReglementComponent,
        ModeReglementDetailComponent,
        ModeReglementDialogComponent,
        ModeReglementDeleteDialogComponent,
        ModeReglementPopupComponent,
        ModeReglementDeletePopupComponent,
    ],
    entryComponents: [
        ModeReglementComponent,
        ModeReglementDialogComponent,
        ModeReglementPopupComponent,
        ModeReglementDeleteDialogComponent,
        ModeReglementDeletePopupComponent,
    ],
    providers: [
        ModeReglementService,
        ModeReglementPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EpetrolstationbackendModeReglementModule {}
