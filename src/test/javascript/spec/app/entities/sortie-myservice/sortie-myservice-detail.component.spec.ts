/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { EpetrolstationbackendTestModule } from '../../../test.module';
import { SortieMyserviceDetailComponent } from '../../../../../../main/webapp/app/entities/sortie-myservice/sortie-myservice-detail.component';
import { SortieMyserviceService } from '../../../../../../main/webapp/app/entities/sortie-myservice/sortie-myservice.service';
import { SortieMyservice } from '../../../../../../main/webapp/app/entities/sortie-myservice/sortie-myservice.model';

describe('Component Tests', () => {

    describe('SortieMyservice Management Detail Component', () => {
        let comp: SortieMyserviceDetailComponent;
        let fixture: ComponentFixture<SortieMyserviceDetailComponent>;
        let service: SortieMyserviceService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EpetrolstationbackendTestModule],
                declarations: [SortieMyserviceDetailComponent],
                providers: [
                    SortieMyserviceService
                ]
            })
            .overrideTemplate(SortieMyserviceDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SortieMyserviceDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SortieMyserviceService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new SortieMyservice(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.sortieMyservice).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
