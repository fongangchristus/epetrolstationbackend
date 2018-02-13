/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { EpetrolstationbackendTestModule } from '../../../test.module';
import { TypeIntervenantComponent } from '../../../../../../main/webapp/app/entities/type-intervenant/type-intervenant.component';
import { TypeIntervenantService } from '../../../../../../main/webapp/app/entities/type-intervenant/type-intervenant.service';
import { TypeIntervenant } from '../../../../../../main/webapp/app/entities/type-intervenant/type-intervenant.model';

describe('Component Tests', () => {

    describe('TypeIntervenant Management Component', () => {
        let comp: TypeIntervenantComponent;
        let fixture: ComponentFixture<TypeIntervenantComponent>;
        let service: TypeIntervenantService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EpetrolstationbackendTestModule],
                declarations: [TypeIntervenantComponent],
                providers: [
                    TypeIntervenantService
                ]
            })
            .overrideTemplate(TypeIntervenantComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TypeIntervenantComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TypeIntervenantService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new TypeIntervenant(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.typeIntervenants[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
