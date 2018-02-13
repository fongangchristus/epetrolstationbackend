import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { Unite } from './unite.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Unite>;

@Injectable()
export class UniteService {

    private resourceUrl =  SERVER_API_URL + 'api/unites';

    constructor(private http: HttpClient) { }

    create(unite: Unite): Observable<EntityResponseType> {
        const copy = this.convert(unite);
        return this.http.post<Unite>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(unite: Unite): Observable<EntityResponseType> {
        const copy = this.convert(unite);
        return this.http.put<Unite>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Unite>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Unite[]>> {
        const options = createRequestOption(req);
        return this.http.get<Unite[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Unite[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Unite = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Unite[]>): HttpResponse<Unite[]> {
        const jsonResponse: Unite[] = res.body;
        const body: Unite[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Unite.
     */
    private convertItemFromServer(unite: Unite): Unite {
        const copy: Unite = Object.assign({}, unite);
        return copy;
    }

    /**
     * Convert a Unite to a JSON which can be sent to the server.
     */
    private convert(unite: Unite): Unite {
        const copy: Unite = Object.assign({}, unite);
        return copy;
    }
}
