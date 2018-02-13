import { BaseEntity } from './../../shared';

export class SortieCarburant implements BaseEntity {
    constructor(
        public id?: number,
        public date?: any,
        public prixTotalht?: number,
        public quantite?: number,
        public quantiteINit?: number,
        public quantiteFinal?: number,
        public prixTTC?: number,
        public interId?: number,
        public carbId?: number,
        public pompId?: number,
        public modeId?: number,
        public tresId?: number,
    ) {
    }
}
