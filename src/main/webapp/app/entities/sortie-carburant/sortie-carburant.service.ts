import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { SortieCarburant } from './sortie-carburant.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<SortieCarburant>;

@Injectable()
export class SortieCarburantService {

    private resourceUrl =  SERVER_API_URL + 'api/sortie-carburants';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(sortieCarburant: SortieCarburant): Observable<EntityResponseType> {
        const copy = this.convert(sortieCarburant);
        return this.http.post<SortieCarburant>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(sortieCarburant: SortieCarburant): Observable<EntityResponseType> {
        const copy = this.convert(sortieCarburant);
        return this.http.put<SortieCarburant>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<SortieCarburant>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<SortieCarburant[]>> {
        const options = createRequestOption(req);
        return this.http.get<SortieCarburant[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<SortieCarburant[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: SortieCarburant = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<SortieCarburant[]>): HttpResponse<SortieCarburant[]> {
        const jsonResponse: SortieCarburant[] = res.body;
        const body: SortieCarburant[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to SortieCarburant.
     */
    private convertItemFromServer(sortieCarburant: SortieCarburant): SortieCarburant {
        const copy: SortieCarburant = Object.assign({}, sortieCarburant);
        copy.date = this.dateUtils
            .convertDateTimeFromServer(sortieCarburant.date);
        return copy;
    }

    /**
     * Convert a SortieCarburant to a JSON which can be sent to the server.
     */
    private convert(sortieCarburant: SortieCarburant): SortieCarburant {
        const copy: SortieCarburant = Object.assign({}, sortieCarburant);

        copy.date = this.dateUtils.toDate(sortieCarburant.date);
        return copy;
    }
}
