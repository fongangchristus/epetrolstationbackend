import { BaseEntity } from './../../shared';

export class SortieProduit implements BaseEntity {
    constructor(
        public id?: number,
        public date?: any,
        public motif?: string,
        public prixTotalht?: number,
        public quantite?: number,
        public prixTTC?: number,
        public modeRegId?: number,
        public clientId?: number,
        public tresorId?: number,
        public prodId?: number,
    ) {
    }
}
