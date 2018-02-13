import { BaseEntity } from './../../shared';

export class Citerne implements BaseEntity {
    constructor(
        public id?: number,
        public volumeInit?: string,
        public volumeMax?: string,
        public hascId?: number,
        public hasuId?: number,
        public haiId?: number,
    ) {
    }
}
