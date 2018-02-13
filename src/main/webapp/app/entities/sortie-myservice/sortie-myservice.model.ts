import { BaseEntity } from './../../shared';

export class SortieMyservice implements BaseEntity {
    constructor(
        public id?: number,
        public date?: any,
        public prixTotalht?: number,
        public prixTTC?: number,
        public servId?: number,
        public hasinterId?: number,
        public hasModeRId?: number,
        public hastresorId?: number,
        public hastreId?: number,
    ) {
    }
}
