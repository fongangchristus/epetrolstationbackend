import { BaseEntity } from './../../shared';

export class Carburant implements BaseEntity {
    constructor(
        public id?: number,
        public designation?: string,
        public soldeInit?: number,
        public prixAchat?: number,
        public prixVente?: number,
        public reference?: number,
        public catCarburantId?: number,
    ) {
    }
}
