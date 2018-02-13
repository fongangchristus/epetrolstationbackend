import { BaseEntity } from './../../shared';

export class Tva implements BaseEntity {
    constructor(
        public id?: number,
        public modeTva?: string,
        public tauxTva?: string,
    ) {
    }
}
