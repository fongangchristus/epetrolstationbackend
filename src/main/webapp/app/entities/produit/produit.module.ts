import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EpetrolstationbackendSharedModule } from '../../shared';
import {
    ProduitService,
    ProduitPopupService,
    ProduitComponent,
    ProduitDetailComponent,
    ProduitDialogComponent,
    ProduitPopupComponent,
    ProduitDeletePopupComponent,
    ProduitDeleteDialogComponent,
    produitRoute,
    produitPopupRoute,
    ProduitResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...produitRoute,
    ...produitPopupRoute,
];

@NgModule({
    imports: [
        EpetrolstationbackendSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        ProduitComponent,
        ProduitDetailComponent,
        ProduitDialogComponent,
        ProduitDeleteDialogComponent,
        ProduitPopupComponent,
        ProduitDeletePopupComponent,
    ],
    entryComponents: [
        ProduitComponent,
        ProduitDialogComponent,
        ProduitPopupComponent,
        ProduitDeleteDialogComponent,
        ProduitDeletePopupComponent,
    ],
    providers: [
        ProduitService,
        ProduitPopupService,
        ProduitResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EpetrolstationbackendProduitModule {}
