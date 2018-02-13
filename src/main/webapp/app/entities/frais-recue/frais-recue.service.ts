import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { FraisRecue } from './frais-recue.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<FraisRecue>;

@Injectable()
export class FraisRecueService {

    private resourceUrl =  SERVER_API_URL + 'api/frais-recues';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(fraisRecue: FraisRecue): Observable<EntityResponseType> {
        const copy = this.convert(fraisRecue);
        return this.http.post<FraisRecue>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(fraisRecue: FraisRecue): Observable<EntityResponseType> {
        const copy = this.convert(fraisRecue);
        return this.http.put<FraisRecue>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<FraisRecue>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<FraisRecue[]>> {
        const options = createRequestOption(req);
        return this.http.get<FraisRecue[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<FraisRecue[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: FraisRecue = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<FraisRecue[]>): HttpResponse<FraisRecue[]> {
        const jsonResponse: FraisRecue[] = res.body;
        const body: FraisRecue[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to FraisRecue.
     */
    private convertItemFromServer(fraisRecue: FraisRecue): FraisRecue {
        const copy: FraisRecue = Object.assign({}, fraisRecue);
        copy.date = this.dateUtils
            .convertDateTimeFromServer(fraisRecue.date);
        return copy;
    }

    /**
     * Convert a FraisRecue to a JSON which can be sent to the server.
     */
    private convert(fraisRecue: FraisRecue): FraisRecue {
        const copy: FraisRecue = Object.assign({}, fraisRecue);

        copy.date = this.dateUtils.toDate(fraisRecue.date);
        return copy;
    }
}
