import { BaseEntity } from './../../shared';

export class TypeTresorerie implements BaseEntity {
    constructor(
        public id?: number,
        public libele?: string,
        public description?: string,
    ) {
    }
}
