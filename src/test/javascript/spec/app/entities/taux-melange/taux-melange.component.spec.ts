/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { EpetrolstationbackendTestModule } from '../../../test.module';
import { TauxMelangeComponent } from '../../../../../../main/webapp/app/entities/taux-melange/taux-melange.component';
import { TauxMelangeService } from '../../../../../../main/webapp/app/entities/taux-melange/taux-melange.service';
import { TauxMelange } from '../../../../../../main/webapp/app/entities/taux-melange/taux-melange.model';

describe('Component Tests', () => {

    describe('TauxMelange Management Component', () => {
        let comp: TauxMelangeComponent;
        let fixture: ComponentFixture<TauxMelangeComponent>;
        let service: TauxMelangeService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EpetrolstationbackendTestModule],
                declarations: [TauxMelangeComponent],
                providers: [
                    TauxMelangeService
                ]
            })
            .overrideTemplate(TauxMelangeComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TauxMelangeComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TauxMelangeService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new TauxMelange(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.tauxMelanges[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
