import { BaseEntity } from './../../shared';

export class Tresorerie implements BaseEntity {
    constructor(
        public id?: number,
        public libele?: string,
        public codeRib?: string,
        public soldeReserve?: string,
        public soldeOuverture?: string,
        public chiffreComptable?: string,
        public hastypetId?: number,
    ) {
    }
}
