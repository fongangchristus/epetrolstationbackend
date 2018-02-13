import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EpetrolstationbackendSharedModule } from '../../shared';
import {
    FraisRecueService,
    FraisRecuePopupService,
    FraisRecueComponent,
    FraisRecueDetailComponent,
    FraisRecueDialogComponent,
    FraisRecuePopupComponent,
    FraisRecueDeletePopupComponent,
    FraisRecueDeleteDialogComponent,
    fraisRecueRoute,
    fraisRecuePopupRoute,
} from './';

const ENTITY_STATES = [
    ...fraisRecueRoute,
    ...fraisRecuePopupRoute,
];

@NgModule({
    imports: [
        EpetrolstationbackendSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        FraisRecueComponent,
        FraisRecueDetailComponent,
        FraisRecueDialogComponent,
        FraisRecueDeleteDialogComponent,
        FraisRecuePopupComponent,
        FraisRecueDeletePopupComponent,
    ],
    entryComponents: [
        FraisRecueComponent,
        FraisRecueDialogComponent,
        FraisRecuePopupComponent,
        FraisRecueDeleteDialogComponent,
        FraisRecueDeletePopupComponent,
    ],
    providers: [
        FraisRecueService,
        FraisRecuePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EpetrolstationbackendFraisRecueModule {}
