import { BaseEntity } from './../../shared';

export class Categorie implements BaseEntity {
    constructor(
        public id?: number,
        public libele?: string,
        public description?: string,
        public hasmanies?: BaseEntity[],
    ) {
    }
}
