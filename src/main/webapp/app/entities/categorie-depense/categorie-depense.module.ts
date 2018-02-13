import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EpetrolstationbackendSharedModule } from '../../shared';
import {
    CategorieDepenseService,
    CategorieDepensePopupService,
    CategorieDepenseComponent,
    CategorieDepenseDetailComponent,
    CategorieDepenseDialogComponent,
    CategorieDepensePopupComponent,
    CategorieDepenseDeletePopupComponent,
    CategorieDepenseDeleteDialogComponent,
    categorieDepenseRoute,
    categorieDepensePopupRoute,
} from './';

const ENTITY_STATES = [
    ...categorieDepenseRoute,
    ...categorieDepensePopupRoute,
];

@NgModule({
    imports: [
        EpetrolstationbackendSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        CategorieDepenseComponent,
        CategorieDepenseDetailComponent,
        CategorieDepenseDialogComponent,
        CategorieDepenseDeleteDialogComponent,
        CategorieDepensePopupComponent,
        CategorieDepenseDeletePopupComponent,
    ],
    entryComponents: [
        CategorieDepenseComponent,
        CategorieDepenseDialogComponent,
        CategorieDepensePopupComponent,
        CategorieDepenseDeleteDialogComponent,
        CategorieDepenseDeletePopupComponent,
    ],
    providers: [
        CategorieDepenseService,
        CategorieDepensePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EpetrolstationbackendCategorieDepenseModule {}
