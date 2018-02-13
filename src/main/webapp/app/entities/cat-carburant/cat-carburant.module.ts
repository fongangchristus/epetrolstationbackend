import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EpetrolstationbackendSharedModule } from '../../shared';
import {
    CatCarburantService,
    CatCarburantPopupService,
    CatCarburantComponent,
    CatCarburantDetailComponent,
    CatCarburantDialogComponent,
    CatCarburantPopupComponent,
    CatCarburantDeletePopupComponent,
    CatCarburantDeleteDialogComponent,
    catCarburantRoute,
    catCarburantPopupRoute,
} from './';

const ENTITY_STATES = [
    ...catCarburantRoute,
    ...catCarburantPopupRoute,
];

@NgModule({
    imports: [
        EpetrolstationbackendSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        CatCarburantComponent,
        CatCarburantDetailComponent,
        CatCarburantDialogComponent,
        CatCarburantDeleteDialogComponent,
        CatCarburantPopupComponent,
        CatCarburantDeletePopupComponent,
    ],
    entryComponents: [
        CatCarburantComponent,
        CatCarburantDialogComponent,
        CatCarburantPopupComponent,
        CatCarburantDeleteDialogComponent,
        CatCarburantDeletePopupComponent,
    ],
    providers: [
        CatCarburantService,
        CatCarburantPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EpetrolstationbackendCatCarburantModule {}
