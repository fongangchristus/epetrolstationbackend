import { BaseEntity } from './../../shared';

export class Unite implements BaseEntity {
    constructor(
        public id?: number,
        public libele?: string,
        public abreviation?: string,
        public equivEnLitre?: string,
    ) {
    }
}
