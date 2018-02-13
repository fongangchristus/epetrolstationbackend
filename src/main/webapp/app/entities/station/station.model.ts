import { BaseEntity } from './../../shared';

export class Station implements BaseEntity {
    constructor(
        public id?: number,
        public code?: string,
        public denomination?: string,
        public telephone?: string,
        public email?: string,
        public fax?: string,
        public adresseId?: number,
        public intervenants?: BaseEntity[],
    ) {
    }
}
