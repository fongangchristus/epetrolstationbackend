import { BaseEntity } from './../../shared';

export class EntreeCiterne implements BaseEntity {
    constructor(
        public id?: number,
        public date?: any,
        public valeurMax?: string,
        public valeurActuel?: string,
        public quantite?: string,
        public hasciterneId?: number,
        public hasuniteId?: number,
        public hasiId?: number,
    ) {
    }
}
