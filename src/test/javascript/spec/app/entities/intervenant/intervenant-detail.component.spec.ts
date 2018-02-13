/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { EpetrolstationbackendTestModule } from '../../../test.module';
import { IntervenantDetailComponent } from '../../../../../../main/webapp/app/entities/intervenant/intervenant-detail.component';
import { IntervenantService } from '../../../../../../main/webapp/app/entities/intervenant/intervenant.service';
import { Intervenant } from '../../../../../../main/webapp/app/entities/intervenant/intervenant.model';

describe('Component Tests', () => {

    describe('Intervenant Management Detail Component', () => {
        let comp: IntervenantDetailComponent;
        let fixture: ComponentFixture<IntervenantDetailComponent>;
        let service: IntervenantService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EpetrolstationbackendTestModule],
                declarations: [IntervenantDetailComponent],
                providers: [
                    IntervenantService
                ]
            })
            .overrideTemplate(IntervenantDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(IntervenantDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(IntervenantService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Intervenant(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.intervenant).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
