import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EpetrolstationbackendSharedModule } from '../../shared';
import {
    SortieMyserviceService,
    SortieMyservicePopupService,
    SortieMyserviceComponent,
    SortieMyserviceDetailComponent,
    SortieMyserviceDialogComponent,
    SortieMyservicePopupComponent,
    SortieMyserviceDeletePopupComponent,
    SortieMyserviceDeleteDialogComponent,
    sortieMyserviceRoute,
    sortieMyservicePopupRoute,
} from './';

const ENTITY_STATES = [
    ...sortieMyserviceRoute,
    ...sortieMyservicePopupRoute,
];

@NgModule({
    imports: [
        EpetrolstationbackendSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        SortieMyserviceComponent,
        SortieMyserviceDetailComponent,
        SortieMyserviceDialogComponent,
        SortieMyserviceDeleteDialogComponent,
        SortieMyservicePopupComponent,
        SortieMyserviceDeletePopupComponent,
    ],
    entryComponents: [
        SortieMyserviceComponent,
        SortieMyserviceDialogComponent,
        SortieMyservicePopupComponent,
        SortieMyserviceDeleteDialogComponent,
        SortieMyserviceDeletePopupComponent,
    ],
    providers: [
        SortieMyserviceService,
        SortieMyservicePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EpetrolstationbackendSortieMyserviceModule {}
