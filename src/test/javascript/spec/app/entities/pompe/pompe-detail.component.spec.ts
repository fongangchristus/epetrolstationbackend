/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { EpetrolstationbackendTestModule } from '../../../test.module';
import { PompeDetailComponent } from '../../../../../../main/webapp/app/entities/pompe/pompe-detail.component';
import { PompeService } from '../../../../../../main/webapp/app/entities/pompe/pompe.service';
import { Pompe } from '../../../../../../main/webapp/app/entities/pompe/pompe.model';

describe('Component Tests', () => {

    describe('Pompe Management Detail Component', () => {
        let comp: PompeDetailComponent;
        let fixture: ComponentFixture<PompeDetailComponent>;
        let service: PompeService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EpetrolstationbackendTestModule],
                declarations: [PompeDetailComponent],
                providers: [
                    PompeService
                ]
            })
            .overrideTemplate(PompeDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PompeDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PompeService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Pompe(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.pompe).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
