/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { EpetrolstationbackendTestModule } from '../../../test.module';
import { TvaDetailComponent } from '../../../../../../main/webapp/app/entities/tva/tva-detail.component';
import { TvaService } from '../../../../../../main/webapp/app/entities/tva/tva.service';
import { Tva } from '../../../../../../main/webapp/app/entities/tva/tva.model';

describe('Component Tests', () => {

    describe('Tva Management Detail Component', () => {
        let comp: TvaDetailComponent;
        let fixture: ComponentFixture<TvaDetailComponent>;
        let service: TvaService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EpetrolstationbackendTestModule],
                declarations: [TvaDetailComponent],
                providers: [
                    TvaService
                ]
            })
            .overrideTemplate(TvaDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TvaDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TvaService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Tva(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.tva).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
