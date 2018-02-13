import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { Tva } from './tva.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Tva>;

@Injectable()
export class TvaService {

    private resourceUrl =  SERVER_API_URL + 'api/tvas';

    constructor(private http: HttpClient) { }

    create(tva: Tva): Observable<EntityResponseType> {
        const copy = this.convert(tva);
        return this.http.post<Tva>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(tva: Tva): Observable<EntityResponseType> {
        const copy = this.convert(tva);
        return this.http.put<Tva>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Tva>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Tva[]>> {
        const options = createRequestOption(req);
        return this.http.get<Tva[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Tva[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Tva = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Tva[]>): HttpResponse<Tva[]> {
        const jsonResponse: Tva[] = res.body;
        const body: Tva[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Tva.
     */
    private convertItemFromServer(tva: Tva): Tva {
        const copy: Tva = Object.assign({}, tva);
        return copy;
    }

    /**
     * Convert a Tva to a JSON which can be sent to the server.
     */
    private convert(tva: Tva): Tva {
        const copy: Tva = Object.assign({}, tva);
        return copy;
    }
}
