import { BaseEntity } from './../../shared';

export class CatCarburant implements BaseEntity {
    constructor(
        public id?: number,
        public libele?: string,
        public description?: string,
        public have?: BaseEntity[],
    ) {
    }
}
