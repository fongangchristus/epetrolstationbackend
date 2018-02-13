import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EpetrolstationbackendSharedModule } from '../../shared';
import {
    TvaService,
    TvaPopupService,
    TvaComponent,
    TvaDetailComponent,
    TvaDialogComponent,
    TvaPopupComponent,
    TvaDeletePopupComponent,
    TvaDeleteDialogComponent,
    tvaRoute,
    tvaPopupRoute,
} from './';

const ENTITY_STATES = [
    ...tvaRoute,
    ...tvaPopupRoute,
];

@NgModule({
    imports: [
        EpetrolstationbackendSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        TvaComponent,
        TvaDetailComponent,
        TvaDialogComponent,
        TvaDeleteDialogComponent,
        TvaPopupComponent,
        TvaDeletePopupComponent,
    ],
    entryComponents: [
        TvaComponent,
        TvaDialogComponent,
        TvaPopupComponent,
        TvaDeleteDialogComponent,
        TvaDeletePopupComponent,
    ],
    providers: [
        TvaService,
        TvaPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EpetrolstationbackendTvaModule {}
