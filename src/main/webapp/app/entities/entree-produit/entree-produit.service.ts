import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { EntreeProduit } from './entree-produit.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<EntreeProduit>;

@Injectable()
export class EntreeProduitService {

    private resourceUrl =  SERVER_API_URL + 'api/entree-produits';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(entreeProduit: EntreeProduit): Observable<EntityResponseType> {
        const copy = this.convert(entreeProduit);
        return this.http.post<EntreeProduit>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(entreeProduit: EntreeProduit): Observable<EntityResponseType> {
        const copy = this.convert(entreeProduit);
        return this.http.put<EntreeProduit>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<EntreeProduit>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<EntreeProduit[]>> {
        const options = createRequestOption(req);
        return this.http.get<EntreeProduit[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<EntreeProduit[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: EntreeProduit = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<EntreeProduit[]>): HttpResponse<EntreeProduit[]> {
        const jsonResponse: EntreeProduit[] = res.body;
        const body: EntreeProduit[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to EntreeProduit.
     */
    private convertItemFromServer(entreeProduit: EntreeProduit): EntreeProduit {
        const copy: EntreeProduit = Object.assign({}, entreeProduit);
        copy.date = this.dateUtils
            .convertDateTimeFromServer(entreeProduit.date);
        return copy;
    }

    /**
     * Convert a EntreeProduit to a JSON which can be sent to the server.
     */
    private convert(entreeProduit: EntreeProduit): EntreeProduit {
        const copy: EntreeProduit = Object.assign({}, entreeProduit);

        copy.date = this.dateUtils.toDate(entreeProduit.date);
        return copy;
    }
}
