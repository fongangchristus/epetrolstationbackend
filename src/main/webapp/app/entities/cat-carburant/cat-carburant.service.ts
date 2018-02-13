import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { CatCarburant } from './cat-carburant.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<CatCarburant>;

@Injectable()
export class CatCarburantService {

    private resourceUrl =  SERVER_API_URL + 'api/cat-carburants';

    constructor(private http: HttpClient) { }

    create(catCarburant: CatCarburant): Observable<EntityResponseType> {
        const copy = this.convert(catCarburant);
        return this.http.post<CatCarburant>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(catCarburant: CatCarburant): Observable<EntityResponseType> {
        const copy = this.convert(catCarburant);
        return this.http.put<CatCarburant>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<CatCarburant>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<CatCarburant[]>> {
        const options = createRequestOption(req);
        return this.http.get<CatCarburant[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<CatCarburant[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: CatCarburant = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<CatCarburant[]>): HttpResponse<CatCarburant[]> {
        const jsonResponse: CatCarburant[] = res.body;
        const body: CatCarburant[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to CatCarburant.
     */
    private convertItemFromServer(catCarburant: CatCarburant): CatCarburant {
        const copy: CatCarburant = Object.assign({}, catCarburant);
        return copy;
    }

    /**
     * Convert a CatCarburant to a JSON which can be sent to the server.
     */
    private convert(catCarburant: CatCarburant): CatCarburant {
        const copy: CatCarburant = Object.assign({}, catCarburant);
        return copy;
    }
}
