import { BaseEntity } from './../../shared';

export class Depense implements BaseEntity {
    constructor(
        public id?: number,
        public date?: any,
        public code?: string,
        public designation?: string,
        public description?: string,
        public montant?: number,
        public hastresroId?: number,
        public hasintervId?: number,
        public useId?: number,
        public hasId?: number,
    ) {
    }
}
