import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EpetrolstationbackendSharedModule } from '../../shared';
import {
    TresorerieService,
    TresoreriePopupService,
    TresorerieComponent,
    TresorerieDetailComponent,
    TresorerieDialogComponent,
    TresoreriePopupComponent,
    TresorerieDeletePopupComponent,
    TresorerieDeleteDialogComponent,
    tresorerieRoute,
    tresoreriePopupRoute,
} from './';

const ENTITY_STATES = [
    ...tresorerieRoute,
    ...tresoreriePopupRoute,
];

@NgModule({
    imports: [
        EpetrolstationbackendSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        TresorerieComponent,
        TresorerieDetailComponent,
        TresorerieDialogComponent,
        TresorerieDeleteDialogComponent,
        TresoreriePopupComponent,
        TresorerieDeletePopupComponent,
    ],
    entryComponents: [
        TresorerieComponent,
        TresorerieDialogComponent,
        TresoreriePopupComponent,
        TresorerieDeleteDialogComponent,
        TresorerieDeletePopupComponent,
    ],
    providers: [
        TresorerieService,
        TresoreriePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EpetrolstationbackendTresorerieModule {}
