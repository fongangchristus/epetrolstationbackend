import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { Myservice } from './myservice.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Myservice>;

@Injectable()
export class MyserviceService {

    private resourceUrl =  SERVER_API_URL + 'api/myservices';

    constructor(private http: HttpClient) { }

    create(myservice: Myservice): Observable<EntityResponseType> {
        const copy = this.convert(myservice);
        return this.http.post<Myservice>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(myservice: Myservice): Observable<EntityResponseType> {
        const copy = this.convert(myservice);
        return this.http.put<Myservice>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Myservice>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Myservice[]>> {
        const options = createRequestOption(req);
        return this.http.get<Myservice[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Myservice[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Myservice = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Myservice[]>): HttpResponse<Myservice[]> {
        const jsonResponse: Myservice[] = res.body;
        const body: Myservice[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Myservice.
     */
    private convertItemFromServer(myservice: Myservice): Myservice {
        const copy: Myservice = Object.assign({}, myservice);
        return copy;
    }

    /**
     * Convert a Myservice to a JSON which can be sent to the server.
     */
    private convert(myservice: Myservice): Myservice {
        const copy: Myservice = Object.assign({}, myservice);
        return copy;
    }
}
