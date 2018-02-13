import { BaseEntity } from './../../shared';

export class ModeReglement implements BaseEntity {
    constructor(
        public id?: number,
        public code?: string,
        public libele?: string,
    ) {
    }
}
