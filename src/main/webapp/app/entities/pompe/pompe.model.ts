import { BaseEntity } from './../../shared';

export class Pompe implements BaseEntity {
    constructor(
        public id?: number,
        public nbChiffre?: string,
        public cInit?: string,
        public melange?: boolean,
        public hasciId?: number,
        public hasreId?: number,
        public hastaId?: number,
        public hascaId?: number,
    ) {
        this.melange = false;
    }
}
