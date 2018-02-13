import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { EntreeCiterne } from './entree-citerne.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<EntreeCiterne>;

@Injectable()
export class EntreeCiterneService {

    private resourceUrl =  SERVER_API_URL + 'api/entree-citernes';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(entreeCiterne: EntreeCiterne): Observable<EntityResponseType> {
        const copy = this.convert(entreeCiterne);
        return this.http.post<EntreeCiterne>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(entreeCiterne: EntreeCiterne): Observable<EntityResponseType> {
        const copy = this.convert(entreeCiterne);
        return this.http.put<EntreeCiterne>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<EntreeCiterne>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<EntreeCiterne[]>> {
        const options = createRequestOption(req);
        return this.http.get<EntreeCiterne[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<EntreeCiterne[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: EntreeCiterne = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<EntreeCiterne[]>): HttpResponse<EntreeCiterne[]> {
        const jsonResponse: EntreeCiterne[] = res.body;
        const body: EntreeCiterne[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to EntreeCiterne.
     */
    private convertItemFromServer(entreeCiterne: EntreeCiterne): EntreeCiterne {
        const copy: EntreeCiterne = Object.assign({}, entreeCiterne);
        copy.date = this.dateUtils
            .convertDateTimeFromServer(entreeCiterne.date);
        return copy;
    }

    /**
     * Convert a EntreeCiterne to a JSON which can be sent to the server.
     */
    private convert(entreeCiterne: EntreeCiterne): EntreeCiterne {
        const copy: EntreeCiterne = Object.assign({}, entreeCiterne);

        copy.date = this.dateUtils.toDate(entreeCiterne.date);
        return copy;
    }
}
