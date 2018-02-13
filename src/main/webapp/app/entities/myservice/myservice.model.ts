import { BaseEntity } from './../../shared';

export class Myservice implements BaseEntity {
    constructor(
        public id?: number,
        public libele?: string,
        public description?: string,
    ) {
    }
}
