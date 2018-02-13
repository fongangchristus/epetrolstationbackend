import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { SortieProduit } from './sortie-produit.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<SortieProduit>;

@Injectable()
export class SortieProduitService {

    private resourceUrl =  SERVER_API_URL + 'api/sortie-produits';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(sortieProduit: SortieProduit): Observable<EntityResponseType> {
        const copy = this.convert(sortieProduit);
        return this.http.post<SortieProduit>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(sortieProduit: SortieProduit): Observable<EntityResponseType> {
        const copy = this.convert(sortieProduit);
        return this.http.put<SortieProduit>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<SortieProduit>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<SortieProduit[]>> {
        const options = createRequestOption(req);
        return this.http.get<SortieProduit[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<SortieProduit[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: SortieProduit = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<SortieProduit[]>): HttpResponse<SortieProduit[]> {
        const jsonResponse: SortieProduit[] = res.body;
        const body: SortieProduit[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to SortieProduit.
     */
    private convertItemFromServer(sortieProduit: SortieProduit): SortieProduit {
        const copy: SortieProduit = Object.assign({}, sortieProduit);
        copy.date = this.dateUtils
            .convertDateTimeFromServer(sortieProduit.date);
        return copy;
    }

    /**
     * Convert a SortieProduit to a JSON which can be sent to the server.
     */
    private convert(sortieProduit: SortieProduit): SortieProduit {
        const copy: SortieProduit = Object.assign({}, sortieProduit);

        copy.date = this.dateUtils.toDate(sortieProduit.date);
        return copy;
    }
}
