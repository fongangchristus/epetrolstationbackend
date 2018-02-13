import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EpetrolstationbackendSharedModule } from '../../shared';
import {
    TypeTresorerieService,
    TypeTresoreriePopupService,
    TypeTresorerieComponent,
    TypeTresorerieDetailComponent,
    TypeTresorerieDialogComponent,
    TypeTresoreriePopupComponent,
    TypeTresorerieDeletePopupComponent,
    TypeTresorerieDeleteDialogComponent,
    typeTresorerieRoute,
    typeTresoreriePopupRoute,
} from './';

const ENTITY_STATES = [
    ...typeTresorerieRoute,
    ...typeTresoreriePopupRoute,
];

@NgModule({
    imports: [
        EpetrolstationbackendSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        TypeTresorerieComponent,
        TypeTresorerieDetailComponent,
        TypeTresorerieDialogComponent,
        TypeTresorerieDeleteDialogComponent,
        TypeTresoreriePopupComponent,
        TypeTresorerieDeletePopupComponent,
    ],
    entryComponents: [
        TypeTresorerieComponent,
        TypeTresorerieDialogComponent,
        TypeTresoreriePopupComponent,
        TypeTresorerieDeleteDialogComponent,
        TypeTresorerieDeletePopupComponent,
    ],
    providers: [
        TypeTresorerieService,
        TypeTresoreriePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EpetrolstationbackendTypeTresorerieModule {}
