import { BaseEntity } from './../../shared';

export class CategorieDepense implements BaseEntity {
    constructor(
        public id?: number,
        public libele?: string,
        public description?: string,
    ) {
    }
}
