import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EpetrolstationbackendSharedModule } from '../../shared';
import {
    TauxMelangeService,
    TauxMelangePopupService,
    TauxMelangeComponent,
    TauxMelangeDetailComponent,
    TauxMelangeDialogComponent,
    TauxMelangePopupComponent,
    TauxMelangeDeletePopupComponent,
    TauxMelangeDeleteDialogComponent,
    tauxMelangeRoute,
    tauxMelangePopupRoute,
} from './';

const ENTITY_STATES = [
    ...tauxMelangeRoute,
    ...tauxMelangePopupRoute,
];

@NgModule({
    imports: [
        EpetrolstationbackendSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        TauxMelangeComponent,
        TauxMelangeDetailComponent,
        TauxMelangeDialogComponent,
        TauxMelangeDeleteDialogComponent,
        TauxMelangePopupComponent,
        TauxMelangeDeletePopupComponent,
    ],
    entryComponents: [
        TauxMelangeComponent,
        TauxMelangeDialogComponent,
        TauxMelangePopupComponent,
        TauxMelangeDeleteDialogComponent,
        TauxMelangeDeletePopupComponent,
    ],
    providers: [
        TauxMelangeService,
        TauxMelangePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EpetrolstationbackendTauxMelangeModule {}
