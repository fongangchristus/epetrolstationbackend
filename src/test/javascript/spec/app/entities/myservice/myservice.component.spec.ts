/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { EpetrolstationbackendTestModule } from '../../../test.module';
import { MyserviceComponent } from '../../../../../../main/webapp/app/entities/myservice/myservice.component';
import { MyserviceService } from '../../../../../../main/webapp/app/entities/myservice/myservice.service';
import { Myservice } from '../../../../../../main/webapp/app/entities/myservice/myservice.model';

describe('Component Tests', () => {

    describe('Myservice Management Component', () => {
        let comp: MyserviceComponent;
        let fixture: ComponentFixture<MyserviceComponent>;
        let service: MyserviceService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EpetrolstationbackendTestModule],
                declarations: [MyserviceComponent],
                providers: [
                    MyserviceService
                ]
            })
            .overrideTemplate(MyserviceComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MyserviceComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MyserviceService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Myservice(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.myservices[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
