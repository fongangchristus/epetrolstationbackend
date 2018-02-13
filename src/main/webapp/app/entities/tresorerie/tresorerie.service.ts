import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { Tresorerie } from './tresorerie.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Tresorerie>;

@Injectable()
export class TresorerieService {

    private resourceUrl =  SERVER_API_URL + 'api/tresoreries';

    constructor(private http: HttpClient) { }

    create(tresorerie: Tresorerie): Observable<EntityResponseType> {
        const copy = this.convert(tresorerie);
        return this.http.post<Tresorerie>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(tresorerie: Tresorerie): Observable<EntityResponseType> {
        const copy = this.convert(tresorerie);
        return this.http.put<Tresorerie>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Tresorerie>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Tresorerie[]>> {
        const options = createRequestOption(req);
        return this.http.get<Tresorerie[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Tresorerie[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Tresorerie = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Tresorerie[]>): HttpResponse<Tresorerie[]> {
        const jsonResponse: Tresorerie[] = res.body;
        const body: Tresorerie[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Tresorerie.
     */
    private convertItemFromServer(tresorerie: Tresorerie): Tresorerie {
        const copy: Tresorerie = Object.assign({}, tresorerie);
        return copy;
    }

    /**
     * Convert a Tresorerie to a JSON which can be sent to the server.
     */
    private convert(tresorerie: Tresorerie): Tresorerie {
        const copy: Tresorerie = Object.assign({}, tresorerie);
        return copy;
    }
}
