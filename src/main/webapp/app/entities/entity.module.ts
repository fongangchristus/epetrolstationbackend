import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { EpetrolstationbackendProduitModule } from './produit/produit.module';
import { EpetrolstationbackendTvaModule } from './tva/tva.module';
import { EpetrolstationbackendCarburantModule } from './carburant/carburant.module';
import { EpetrolstationbackendCatCarburantModule } from './cat-carburant/cat-carburant.module';
import { EpetrolstationbackendCategorieModule } from './categorie/categorie.module';
import { EpetrolstationbackendEntreeProduitModule } from './entree-produit/entree-produit.module';
import { EpetrolstationbackendSortieProduitModule } from './sortie-produit/sortie-produit.module';
import { EpetrolstationbackendEntreeCarburantModule } from './entree-carburant/entree-carburant.module';
import { EpetrolstationbackendSortieCarburantModule } from './sortie-carburant/sortie-carburant.module';
import { EpetrolstationbackendIntervenantModule } from './intervenant/intervenant.module';
import { EpetrolstationbackendTypeIntervenantModule } from './type-intervenant/type-intervenant.module';
import { EpetrolstationbackendStationModule } from './station/station.module';
import { EpetrolstationbackendAdresseModule } from './adresse/adresse.module';
import { EpetrolstationbackendUniteModule } from './unite/unite.module';
import { EpetrolstationbackendModeReglementModule } from './mode-reglement/mode-reglement.module';
import { EpetrolstationbackendCiterneModule } from './citerne/citerne.module';
import { EpetrolstationbackendEntreeCiterneModule } from './entree-citerne/entree-citerne.module';
import { EpetrolstationbackendMyserviceModule } from './myservice/myservice.module';
import { EpetrolstationbackendSortieMyserviceModule } from './sortie-myservice/sortie-myservice.module';
import { EpetrolstationbackendCategorieDepenseModule } from './categorie-depense/categorie-depense.module';
import { EpetrolstationbackendDepenseModule } from './depense/depense.module';
import { EpetrolstationbackendPompeModule } from './pompe/pompe.module';
import { EpetrolstationbackendReservoirModule } from './reservoir/reservoir.module';
import { EpetrolstationbackendTauxMelangeModule } from './taux-melange/taux-melange.module';
import { EpetrolstationbackendFraisRecueModule } from './frais-recue/frais-recue.module';
import { EpetrolstationbackendTresorerieModule } from './tresorerie/tresorerie.module';
import { EpetrolstationbackendTypeTresorerieModule } from './type-tresorerie/type-tresorerie.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        EpetrolstationbackendProduitModule,
        EpetrolstationbackendTvaModule,
        EpetrolstationbackendCarburantModule,
        EpetrolstationbackendCatCarburantModule,
        EpetrolstationbackendCategorieModule,
        EpetrolstationbackendEntreeProduitModule,
        EpetrolstationbackendSortieProduitModule,
        EpetrolstationbackendEntreeCarburantModule,
        EpetrolstationbackendSortieCarburantModule,
        EpetrolstationbackendIntervenantModule,
        EpetrolstationbackendTypeIntervenantModule,
        EpetrolstationbackendStationModule,
        EpetrolstationbackendAdresseModule,
        EpetrolstationbackendUniteModule,
        EpetrolstationbackendModeReglementModule,
        EpetrolstationbackendCiterneModule,
        EpetrolstationbackendEntreeCiterneModule,
        EpetrolstationbackendMyserviceModule,
        EpetrolstationbackendSortieMyserviceModule,
        EpetrolstationbackendCategorieDepenseModule,
        EpetrolstationbackendDepenseModule,
        EpetrolstationbackendPompeModule,
        EpetrolstationbackendReservoirModule,
        EpetrolstationbackendTauxMelangeModule,
        EpetrolstationbackendFraisRecueModule,
        EpetrolstationbackendTresorerieModule,
        EpetrolstationbackendTypeTresorerieModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EpetrolstationbackendEntityModule {}
