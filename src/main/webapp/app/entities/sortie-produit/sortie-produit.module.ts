import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EpetrolstationbackendSharedModule } from '../../shared';
import {
    SortieProduitService,
    SortieProduitPopupService,
    SortieProduitComponent,
    SortieProduitDetailComponent,
    SortieProduitDialogComponent,
    SortieProduitPopupComponent,
    SortieProduitDeletePopupComponent,
    SortieProduitDeleteDialogComponent,
    sortieProduitRoute,
    sortieProduitPopupRoute,
} from './';

const ENTITY_STATES = [
    ...sortieProduitRoute,
    ...sortieProduitPopupRoute,
];

@NgModule({
    imports: [
        EpetrolstationbackendSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        SortieProduitComponent,
        SortieProduitDetailComponent,
        SortieProduitDialogComponent,
        SortieProduitDeleteDialogComponent,
        SortieProduitPopupComponent,
        SortieProduitDeletePopupComponent,
    ],
    entryComponents: [
        SortieProduitComponent,
        SortieProduitDialogComponent,
        SortieProduitPopupComponent,
        SortieProduitDeleteDialogComponent,
        SortieProduitDeletePopupComponent,
    ],
    providers: [
        SortieProduitService,
        SortieProduitPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EpetrolstationbackendSortieProduitModule {}
