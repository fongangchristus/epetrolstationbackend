import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { SortieMyservice } from './sortie-myservice.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<SortieMyservice>;

@Injectable()
export class SortieMyserviceService {

    private resourceUrl =  SERVER_API_URL + 'api/sortie-myservices';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(sortieMyservice: SortieMyservice): Observable<EntityResponseType> {
        const copy = this.convert(sortieMyservice);
        return this.http.post<SortieMyservice>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(sortieMyservice: SortieMyservice): Observable<EntityResponseType> {
        const copy = this.convert(sortieMyservice);
        return this.http.put<SortieMyservice>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<SortieMyservice>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<SortieMyservice[]>> {
        const options = createRequestOption(req);
        return this.http.get<SortieMyservice[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<SortieMyservice[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: SortieMyservice = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<SortieMyservice[]>): HttpResponse<SortieMyservice[]> {
        const jsonResponse: SortieMyservice[] = res.body;
        const body: SortieMyservice[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to SortieMyservice.
     */
    private convertItemFromServer(sortieMyservice: SortieMyservice): SortieMyservice {
        const copy: SortieMyservice = Object.assign({}, sortieMyservice);
        copy.date = this.dateUtils
            .convertDateTimeFromServer(sortieMyservice.date);
        return copy;
    }

    /**
     * Convert a SortieMyservice to a JSON which can be sent to the server.
     */
    private convert(sortieMyservice: SortieMyservice): SortieMyservice {
        const copy: SortieMyservice = Object.assign({}, sortieMyservice);

        copy.date = this.dateUtils.toDate(sortieMyservice.date);
        return copy;
    }
}
