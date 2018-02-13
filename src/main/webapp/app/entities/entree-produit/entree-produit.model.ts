import { BaseEntity } from './../../shared';

export class EntreeProduit implements BaseEntity {
    constructor(
        public id?: number,
        public date?: any,
        public motif?: string,
        public prixTotalht?: number,
        public quantite?: number,
        public prixTTC?: number,
        public modeRId?: number,
        public tresorerieId?: number,
        public produitId?: number,
        public commercials?: BaseEntity[],
    ) {
    }
}
