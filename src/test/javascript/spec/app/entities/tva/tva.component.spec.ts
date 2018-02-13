/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { EpetrolstationbackendTestModule } from '../../../test.module';
import { TvaComponent } from '../../../../../../main/webapp/app/entities/tva/tva.component';
import { TvaService } from '../../../../../../main/webapp/app/entities/tva/tva.service';
import { Tva } from '../../../../../../main/webapp/app/entities/tva/tva.model';

describe('Component Tests', () => {

    describe('Tva Management Component', () => {
        let comp: TvaComponent;
        let fixture: ComponentFixture<TvaComponent>;
        let service: TvaService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EpetrolstationbackendTestModule],
                declarations: [TvaComponent],
                providers: [
                    TvaService
                ]
            })
            .overrideTemplate(TvaComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TvaComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TvaService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Tva(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.tvas[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
