import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { Produit } from './produit.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Produit>;

@Injectable()
export class ProduitService {

    private resourceUrl =  SERVER_API_URL + 'api/produits';

    constructor(private http: HttpClient) { }

    create(produit: Produit): Observable<EntityResponseType> {
        const copy = this.convert(produit);
        return this.http.post<Produit>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(produit: Produit): Observable<EntityResponseType> {
        const copy = this.convert(produit);
        return this.http.put<Produit>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Produit>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Produit[]>> {
        const options = createRequestOption(req);
        return this.http.get<Produit[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Produit[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Produit = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Produit[]>): HttpResponse<Produit[]> {
        const jsonResponse: Produit[] = res.body;
        const body: Produit[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Produit.
     */
    private convertItemFromServer(produit: Produit): Produit {
        const copy: Produit = Object.assign({}, produit);
        return copy;
    }

    /**
     * Convert a Produit to a JSON which can be sent to the server.
     */
    private convert(produit: Produit): Produit {
        const copy: Produit = Object.assign({}, produit);
        return copy;
    }
}
