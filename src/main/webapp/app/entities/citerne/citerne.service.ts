import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { Citerne } from './citerne.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Citerne>;

@Injectable()
export class CiterneService {

    private resourceUrl =  SERVER_API_URL + 'api/citernes';

    constructor(private http: HttpClient) { }

    create(citerne: Citerne): Observable<EntityResponseType> {
        const copy = this.convert(citerne);
        return this.http.post<Citerne>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(citerne: Citerne): Observable<EntityResponseType> {
        const copy = this.convert(citerne);
        return this.http.put<Citerne>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Citerne>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Citerne[]>> {
        const options = createRequestOption(req);
        return this.http.get<Citerne[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Citerne[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Citerne = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Citerne[]>): HttpResponse<Citerne[]> {
        const jsonResponse: Citerne[] = res.body;
        const body: Citerne[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Citerne.
     */
    private convertItemFromServer(citerne: Citerne): Citerne {
        const copy: Citerne = Object.assign({}, citerne);
        return copy;
    }

    /**
     * Convert a Citerne to a JSON which can be sent to the server.
     */
    private convert(citerne: Citerne): Citerne {
        const copy: Citerne = Object.assign({}, citerne);
        return copy;
    }
}
