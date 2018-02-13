import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { EntreeCarburant } from './entree-carburant.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<EntreeCarburant>;

@Injectable()
export class EntreeCarburantService {

    private resourceUrl =  SERVER_API_URL + 'api/entree-carburants';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(entreeCarburant: EntreeCarburant): Observable<EntityResponseType> {
        const copy = this.convert(entreeCarburant);
        return this.http.post<EntreeCarburant>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(entreeCarburant: EntreeCarburant): Observable<EntityResponseType> {
        const copy = this.convert(entreeCarburant);
        return this.http.put<EntreeCarburant>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<EntreeCarburant>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<EntreeCarburant[]>> {
        const options = createRequestOption(req);
        return this.http.get<EntreeCarburant[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<EntreeCarburant[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: EntreeCarburant = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<EntreeCarburant[]>): HttpResponse<EntreeCarburant[]> {
        const jsonResponse: EntreeCarburant[] = res.body;
        const body: EntreeCarburant[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to EntreeCarburant.
     */
    private convertItemFromServer(entreeCarburant: EntreeCarburant): EntreeCarburant {
        const copy: EntreeCarburant = Object.assign({}, entreeCarburant);
        copy.date = this.dateUtils
            .convertDateTimeFromServer(entreeCarburant.date);
        return copy;
    }

    /**
     * Convert a EntreeCarburant to a JSON which can be sent to the server.
     */
    private convert(entreeCarburant: EntreeCarburant): EntreeCarburant {
        const copy: EntreeCarburant = Object.assign({}, entreeCarburant);

        copy.date = this.dateUtils.toDate(entreeCarburant.date);
        return copy;
    }
}
