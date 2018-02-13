import { BaseEntity } from './../../shared';

export class Produit implements BaseEntity {
    constructor(
        public id?: number,
        public designation?: string,
        public soldeInit?: number,
        public prixAchat?: number,
        public prixVente?: number,
        public quantiteDispo?: number,
        public quantiteInit?: number,
        public seuilReaprov?: number,
        public reference?: number,
        public tvaId?: number,
        public categorieId?: number,
    ) {
    }
}
