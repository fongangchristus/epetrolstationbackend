import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { TypeTresorerie } from './type-tresorerie.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<TypeTresorerie>;

@Injectable()
export class TypeTresorerieService {

    private resourceUrl =  SERVER_API_URL + 'api/type-tresoreries';

    constructor(private http: HttpClient) { }

    create(typeTresorerie: TypeTresorerie): Observable<EntityResponseType> {
        const copy = this.convert(typeTresorerie);
        return this.http.post<TypeTresorerie>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(typeTresorerie: TypeTresorerie): Observable<EntityResponseType> {
        const copy = this.convert(typeTresorerie);
        return this.http.put<TypeTresorerie>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<TypeTresorerie>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<TypeTresorerie[]>> {
        const options = createRequestOption(req);
        return this.http.get<TypeTresorerie[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<TypeTresorerie[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: TypeTresorerie = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<TypeTresorerie[]>): HttpResponse<TypeTresorerie[]> {
        const jsonResponse: TypeTresorerie[] = res.body;
        const body: TypeTresorerie[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to TypeTresorerie.
     */
    private convertItemFromServer(typeTresorerie: TypeTresorerie): TypeTresorerie {
        const copy: TypeTresorerie = Object.assign({}, typeTresorerie);
        return copy;
    }

    /**
     * Convert a TypeTresorerie to a JSON which can be sent to the server.
     */
    private convert(typeTresorerie: TypeTresorerie): TypeTresorerie {
        const copy: TypeTresorerie = Object.assign({}, typeTresorerie);
        return copy;
    }
}
