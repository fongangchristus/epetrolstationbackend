import { BaseEntity } from './../../shared';

export class FraisRecue implements BaseEntity {
    constructor(
        public id?: number,
        public date?: any,
        public montant?: number,
        public hastrrId?: number,
    ) {
    }
}
