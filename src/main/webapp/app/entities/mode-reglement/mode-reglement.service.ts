import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { ModeReglement } from './mode-reglement.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<ModeReglement>;

@Injectable()
export class ModeReglementService {

    private resourceUrl =  SERVER_API_URL + 'api/mode-reglements';

    constructor(private http: HttpClient) { }

    create(modeReglement: ModeReglement): Observable<EntityResponseType> {
        const copy = this.convert(modeReglement);
        return this.http.post<ModeReglement>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(modeReglement: ModeReglement): Observable<EntityResponseType> {
        const copy = this.convert(modeReglement);
        return this.http.put<ModeReglement>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ModeReglement>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<ModeReglement[]>> {
        const options = createRequestOption(req);
        return this.http.get<ModeReglement[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<ModeReglement[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: ModeReglement = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<ModeReglement[]>): HttpResponse<ModeReglement[]> {
        const jsonResponse: ModeReglement[] = res.body;
        const body: ModeReglement[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to ModeReglement.
     */
    private convertItemFromServer(modeReglement: ModeReglement): ModeReglement {
        const copy: ModeReglement = Object.assign({}, modeReglement);
        return copy;
    }

    /**
     * Convert a ModeReglement to a JSON which can be sent to the server.
     */
    private convert(modeReglement: ModeReglement): ModeReglement {
        const copy: ModeReglement = Object.assign({}, modeReglement);
        return copy;
    }
}
