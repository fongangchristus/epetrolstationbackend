import { BaseEntity } from './../../shared';

export class TypeIntervenant implements BaseEntity {
    constructor(
        public id?: number,
        public libele?: string,
        public description?: string,
    ) {
    }
}
