/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { EpetrolstationbackendTestModule } from '../../../test.module';
import { CiterneDetailComponent } from '../../../../../../main/webapp/app/entities/citerne/citerne-detail.component';
import { CiterneService } from '../../../../../../main/webapp/app/entities/citerne/citerne.service';
import { Citerne } from '../../../../../../main/webapp/app/entities/citerne/citerne.model';

describe('Component Tests', () => {

    describe('Citerne Management Detail Component', () => {
        let comp: CiterneDetailComponent;
        let fixture: ComponentFixture<CiterneDetailComponent>;
        let service: CiterneService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EpetrolstationbackendTestModule],
                declarations: [CiterneDetailComponent],
                providers: [
                    CiterneService
                ]
            })
            .overrideTemplate(CiterneDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CiterneDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CiterneService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Citerne(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.citerne).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
