import { BaseEntity } from './../../shared';

export class Reservoir implements BaseEntity {
    constructor(
        public id?: number,
        public designation?: string,
        public quantiteInit?: string,
        public quantiteMax?: string,
        public quantiteAlerte?: string,
    ) {
    }
}
