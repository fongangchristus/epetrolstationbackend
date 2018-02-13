import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { TypeIntervenant } from './type-intervenant.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<TypeIntervenant>;

@Injectable()
export class TypeIntervenantService {

    private resourceUrl =  SERVER_API_URL + 'api/type-intervenants';

    constructor(private http: HttpClient) { }

    create(typeIntervenant: TypeIntervenant): Observable<EntityResponseType> {
        const copy = this.convert(typeIntervenant);
        return this.http.post<TypeIntervenant>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(typeIntervenant: TypeIntervenant): Observable<EntityResponseType> {
        const copy = this.convert(typeIntervenant);
        return this.http.put<TypeIntervenant>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<TypeIntervenant>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<TypeIntervenant[]>> {
        const options = createRequestOption(req);
        return this.http.get<TypeIntervenant[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<TypeIntervenant[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: TypeIntervenant = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<TypeIntervenant[]>): HttpResponse<TypeIntervenant[]> {
        const jsonResponse: TypeIntervenant[] = res.body;
        const body: TypeIntervenant[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to TypeIntervenant.
     */
    private convertItemFromServer(typeIntervenant: TypeIntervenant): TypeIntervenant {
        const copy: TypeIntervenant = Object.assign({}, typeIntervenant);
        return copy;
    }

    /**
     * Convert a TypeIntervenant to a JSON which can be sent to the server.
     */
    private convert(typeIntervenant: TypeIntervenant): TypeIntervenant {
        const copy: TypeIntervenant = Object.assign({}, typeIntervenant);
        return copy;
    }
}
