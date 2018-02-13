import { BaseEntity } from './../../shared';

export class Adresse implements BaseEntity {
    constructor(
        public id?: number,
        public pays?: string,
        public region?: string,
        public ville?: string,
        public quartier?: string,
        public codePostal?: string,
    ) {
    }
}
