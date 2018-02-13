import { BaseEntity } from './../../shared';

export class Intervenant implements BaseEntity {
    constructor(
        public id?: number,
        public imageFile?: string,
        public nomComplet?: string,
        public fonction?: string,
        public cni?: string,
        public tel?: string,
        public addresse?: string,
        public soldeInit?: string,
        public entreeProduitId?: number,
        public entreeCarburantId?: number,
        public managerId?: number,
        public typeIntervenantId?: number,
        public isId?: number,
        public stationId?: number,
    ) {
    }
}
