/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { EpetrolstationbackendTestModule } from '../../../test.module';
import { IntervenantComponent } from '../../../../../../main/webapp/app/entities/intervenant/intervenant.component';
import { IntervenantService } from '../../../../../../main/webapp/app/entities/intervenant/intervenant.service';
import { Intervenant } from '../../../../../../main/webapp/app/entities/intervenant/intervenant.model';

describe('Component Tests', () => {

    describe('Intervenant Management Component', () => {
        let comp: IntervenantComponent;
        let fixture: ComponentFixture<IntervenantComponent>;
        let service: IntervenantService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EpetrolstationbackendTestModule],
                declarations: [IntervenantComponent],
                providers: [
                    IntervenantService
                ]
            })
            .overrideTemplate(IntervenantComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(IntervenantComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(IntervenantService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Intervenant(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.intervenants[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
