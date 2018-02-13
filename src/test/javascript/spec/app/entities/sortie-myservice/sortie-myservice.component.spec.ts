/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { EpetrolstationbackendTestModule } from '../../../test.module';
import { SortieMyserviceComponent } from '../../../../../../main/webapp/app/entities/sortie-myservice/sortie-myservice.component';
import { SortieMyserviceService } from '../../../../../../main/webapp/app/entities/sortie-myservice/sortie-myservice.service';
import { SortieMyservice } from '../../../../../../main/webapp/app/entities/sortie-myservice/sortie-myservice.model';

describe('Component Tests', () => {

    describe('SortieMyservice Management Component', () => {
        let comp: SortieMyserviceComponent;
        let fixture: ComponentFixture<SortieMyserviceComponent>;
        let service: SortieMyserviceService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EpetrolstationbackendTestModule],
                declarations: [SortieMyserviceComponent],
                providers: [
                    SortieMyserviceService
                ]
            })
            .overrideTemplate(SortieMyserviceComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SortieMyserviceComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SortieMyserviceService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new SortieMyservice(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.sortieMyservices[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
