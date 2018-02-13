/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { EpetrolstationbackendTestModule } from '../../../test.module';
import { MyserviceDetailComponent } from '../../../../../../main/webapp/app/entities/myservice/myservice-detail.component';
import { MyserviceService } from '../../../../../../main/webapp/app/entities/myservice/myservice.service';
import { Myservice } from '../../../../../../main/webapp/app/entities/myservice/myservice.model';

describe('Component Tests', () => {

    describe('Myservice Management Detail Component', () => {
        let comp: MyserviceDetailComponent;
        let fixture: ComponentFixture<MyserviceDetailComponent>;
        let service: MyserviceService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EpetrolstationbackendTestModule],
                declarations: [MyserviceDetailComponent],
                providers: [
                    MyserviceService
                ]
            })
            .overrideTemplate(MyserviceDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MyserviceDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MyserviceService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Myservice(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.myservice).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
