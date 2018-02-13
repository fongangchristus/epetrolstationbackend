import { BaseEntity } from './../../shared';

export class EntreeCarburant implements BaseEntity {
    constructor(
        public id?: number,
        public date?: any,
        public prixTotalht?: number,
        public quantite?: number,
        public prixTTC?: number,
        public modeReglementId?: number,
        public carburantId?: number,
        public tresorerieId?: number,
        public pompistes?: BaseEntity[],
    ) {
    }
}
