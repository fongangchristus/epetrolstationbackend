import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EpetrolstationbackendSharedModule } from '../../shared';
import {
    SortieCarburantService,
    SortieCarburantPopupService,
    SortieCarburantComponent,
    SortieCarburantDetailComponent,
    SortieCarburantDialogComponent,
    SortieCarburantPopupComponent,
    SortieCarburantDeletePopupComponent,
    SortieCarburantDeleteDialogComponent,
    sortieCarburantRoute,
    sortieCarburantPopupRoute,
} from './';

const ENTITY_STATES = [
    ...sortieCarburantRoute,
    ...sortieCarburantPopupRoute,
];

@NgModule({
    imports: [
        EpetrolstationbackendSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        SortieCarburantComponent,
        SortieCarburantDetailComponent,
        SortieCarburantDialogComponent,
        SortieCarburantDeleteDialogComponent,
        SortieCarburantPopupComponent,
        SortieCarburantDeletePopupComponent,
    ],
    entryComponents: [
        SortieCarburantComponent,
        SortieCarburantDialogComponent,
        SortieCarburantPopupComponent,
        SortieCarburantDeleteDialogComponent,
        SortieCarburantDeletePopupComponent,
    ],
    providers: [
        SortieCarburantService,
        SortieCarburantPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EpetrolstationbackendSortieCarburantModule {}
