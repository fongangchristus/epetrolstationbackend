import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { Intervenant } from './intervenant.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Intervenant>;

@Injectable()
export class IntervenantService {

    private resourceUrl =  SERVER_API_URL + 'api/intervenants';

    constructor(private http: HttpClient) { }

    create(intervenant: Intervenant): Observable<EntityResponseType> {
        const copy = this.convert(intervenant);
        return this.http.post<Intervenant>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(intervenant: Intervenant): Observable<EntityResponseType> {
        const copy = this.convert(intervenant);
        return this.http.put<Intervenant>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Intervenant>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Intervenant[]>> {
        const options = createRequestOption(req);
        return this.http.get<Intervenant[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Intervenant[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Intervenant = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Intervenant[]>): HttpResponse<Intervenant[]> {
        const jsonResponse: Intervenant[] = res.body;
        const body: Intervenant[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Intervenant.
     */
    private convertItemFromServer(intervenant: Intervenant): Intervenant {
        const copy: Intervenant = Object.assign({}, intervenant);
        return copy;
    }

    /**
     * Convert a Intervenant to a JSON which can be sent to the server.
     */
    private convert(intervenant: Intervenant): Intervenant {
        const copy: Intervenant = Object.assign({}, intervenant);
        return copy;
    }
}
