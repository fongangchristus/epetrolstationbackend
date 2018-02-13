import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { CategorieDepense } from './categorie-depense.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<CategorieDepense>;

@Injectable()
export class CategorieDepenseService {

    private resourceUrl =  SERVER_API_URL + 'api/categorie-depenses';

    constructor(private http: HttpClient) { }

    create(categorieDepense: CategorieDepense): Observable<EntityResponseType> {
        const copy = this.convert(categorieDepense);
        return this.http.post<CategorieDepense>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(categorieDepense: CategorieDepense): Observable<EntityResponseType> {
        const copy = this.convert(categorieDepense);
        return this.http.put<CategorieDepense>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<CategorieDepense>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<CategorieDepense[]>> {
        const options = createRequestOption(req);
        return this.http.get<CategorieDepense[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<CategorieDepense[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: CategorieDepense = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<CategorieDepense[]>): HttpResponse<CategorieDepense[]> {
        const jsonResponse: CategorieDepense[] = res.body;
        const body: CategorieDepense[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to CategorieDepense.
     */
    private convertItemFromServer(categorieDepense: CategorieDepense): CategorieDepense {
        const copy: CategorieDepense = Object.assign({}, categorieDepense);
        return copy;
    }

    /**
     * Convert a CategorieDepense to a JSON which can be sent to the server.
     */
    private convert(categorieDepense: CategorieDepense): CategorieDepense {
        const copy: CategorieDepense = Object.assign({}, categorieDepense);
        return copy;
    }
}
