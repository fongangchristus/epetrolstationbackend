import { BaseEntity } from './../../shared';

export class TauxMelange implements BaseEntity {
    constructor(
        public id?: number,
        public tauxMelange?: number,
        public prixMelange?: number,
        public tauxEnCours?: boolean,
    ) {
        this.tauxEnCours = false;
    }
}
