/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { EpetrolstationbackendTestModule } from '../../../test.module';
import { TypeIntervenantDetailComponent } from '../../../../../../main/webapp/app/entities/type-intervenant/type-intervenant-detail.component';
import { TypeIntervenantService } from '../../../../../../main/webapp/app/entities/type-intervenant/type-intervenant.service';
import { TypeIntervenant } from '../../../../../../main/webapp/app/entities/type-intervenant/type-intervenant.model';

describe('Component Tests', () => {

    describe('TypeIntervenant Management Detail Component', () => {
        let comp: TypeIntervenantDetailComponent;
        let fixture: ComponentFixture<TypeIntervenantDetailComponent>;
        let service: TypeIntervenantService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EpetrolstationbackendTestModule],
                declarations: [TypeIntervenantDetailComponent],
                providers: [
                    TypeIntervenantService
                ]
            })
            .overrideTemplate(TypeIntervenantDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TypeIntervenantDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TypeIntervenantService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new TypeIntervenant(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.typeIntervenant).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
