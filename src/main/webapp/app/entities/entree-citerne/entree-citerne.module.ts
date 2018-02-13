import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EpetrolstationbackendSharedModule } from '../../shared';
import {
    EntreeCiterneService,
    EntreeCiternePopupService,
    EntreeCiterneComponent,
    EntreeCiterneDetailComponent,
    EntreeCiterneDialogComponent,
    EntreeCiternePopupComponent,
    EntreeCiterneDeletePopupComponent,
    EntreeCiterneDeleteDialogComponent,
    entreeCiterneRoute,
    entreeCiternePopupRoute,
} from './';

const ENTITY_STATES = [
    ...entreeCiterneRoute,
    ...entreeCiternePopupRoute,
];

@NgModule({
    imports: [
        EpetrolstationbackendSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        EntreeCiterneComponent,
        EntreeCiterneDetailComponent,
        EntreeCiterneDialogComponent,
        EntreeCiterneDeleteDialogComponent,
        EntreeCiternePopupComponent,
        EntreeCiterneDeletePopupComponent,
    ],
    entryComponents: [
        EntreeCiterneComponent,
        EntreeCiterneDialogComponent,
        EntreeCiternePopupComponent,
        EntreeCiterneDeleteDialogComponent,
        EntreeCiterneDeletePopupComponent,
    ],
    providers: [
        EntreeCiterneService,
        EntreeCiternePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EpetrolstationbackendEntreeCiterneModule {}
