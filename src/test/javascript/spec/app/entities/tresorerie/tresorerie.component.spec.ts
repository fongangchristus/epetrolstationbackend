/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { EpetrolstationbackendTestModule } from '../../../test.module';
import { TresorerieComponent } from '../../../../../../main/webapp/app/entities/tresorerie/tresorerie.component';
import { TresorerieService } from '../../../../../../main/webapp/app/entities/tresorerie/tresorerie.service';
import { Tresorerie } from '../../../../../../main/webapp/app/entities/tresorerie/tresorerie.model';

describe('Component Tests', () => {

    describe('Tresorerie Management Component', () => {
        let comp: TresorerieComponent;
        let fixture: ComponentFixture<TresorerieComponent>;
        let service: TresorerieService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EpetrolstationbackendTestModule],
                declarations: [TresorerieComponent],
                providers: [
                    TresorerieService
                ]
            })
            .overrideTemplate(TresorerieComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TresorerieComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TresorerieService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Tresorerie(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.tresoreries[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
