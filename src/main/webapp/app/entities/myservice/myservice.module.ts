import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EpetrolstationbackendSharedModule } from '../../shared';
import {
    MyserviceService,
    MyservicePopupService,
    MyserviceComponent,
    MyserviceDetailComponent,
    MyserviceDialogComponent,
    MyservicePopupComponent,
    MyserviceDeletePopupComponent,
    MyserviceDeleteDialogComponent,
    myserviceRoute,
    myservicePopupRoute,
} from './';

const ENTITY_STATES = [
    ...myserviceRoute,
    ...myservicePopupRoute,
];

@NgModule({
    imports: [
        EpetrolstationbackendSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        MyserviceComponent,
        MyserviceDetailComponent,
        MyserviceDialogComponent,
        MyserviceDeleteDialogComponent,
        MyservicePopupComponent,
        MyserviceDeletePopupComponent,
    ],
    entryComponents: [
        MyserviceComponent,
        MyserviceDialogComponent,
        MyservicePopupComponent,
        MyserviceDeleteDialogComponent,
        MyserviceDeletePopupComponent,
    ],
    providers: [
        MyserviceService,
        MyservicePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EpetrolstationbackendMyserviceModule {}
