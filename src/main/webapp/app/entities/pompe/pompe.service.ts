import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { Pompe } from './pompe.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Pompe>;

@Injectable()
export class PompeService {

    private resourceUrl =  SERVER_API_URL + 'api/pompes';

    constructor(private http: HttpClient) { }

    create(pompe: Pompe): Observable<EntityResponseType> {
        const copy = this.convert(pompe);
        return this.http.post<Pompe>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(pompe: Pompe): Observable<EntityResponseType> {
        const copy = this.convert(pompe);
        return this.http.put<Pompe>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Pompe>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Pompe[]>> {
        const options = createRequestOption(req);
        return this.http.get<Pompe[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Pompe[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Pompe = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Pompe[]>): HttpResponse<Pompe[]> {
        const jsonResponse: Pompe[] = res.body;
        const body: Pompe[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Pompe.
     */
    private convertItemFromServer(pompe: Pompe): Pompe {
        const copy: Pompe = Object.assign({}, pompe);
        return copy;
    }

    /**
     * Convert a Pompe to a JSON which can be sent to the server.
     */
    private convert(pompe: Pompe): Pompe {
        const copy: Pompe = Object.assign({}, pompe);
        return copy;
    }
}
