import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EpetrolstationbackendSharedModule } from '../../shared';
import {
    EntreeProduitService,
    EntreeProduitPopupService,
    EntreeProduitComponent,
    EntreeProduitDetailComponent,
    EntreeProduitDialogComponent,
    EntreeProduitPopupComponent,
    EntreeProduitDeletePopupComponent,
    EntreeProduitDeleteDialogComponent,
    entreeProduitRoute,
    entreeProduitPopupRoute,
} from './';

const ENTITY_STATES = [
    ...entreeProduitRoute,
    ...entreeProduitPopupRoute,
];

@NgModule({
    imports: [
        EpetrolstationbackendSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        EntreeProduitComponent,
        EntreeProduitDetailComponent,
        EntreeProduitDialogComponent,
        EntreeProduitDeleteDialogComponent,
        EntreeProduitPopupComponent,
        EntreeProduitDeletePopupComponent,
    ],
    entryComponents: [
        EntreeProduitComponent,
        EntreeProduitDialogComponent,
        EntreeProduitPopupComponent,
        EntreeProduitDeleteDialogComponent,
        EntreeProduitDeletePopupComponent,
    ],
    providers: [
        EntreeProduitService,
        EntreeProduitPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EpetrolstationbackendEntreeProduitModule {}
