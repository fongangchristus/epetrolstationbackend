/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { EpetrolstationbackendTestModule } from '../../../test.module';
import { TauxMelangeDetailComponent } from '../../../../../../main/webapp/app/entities/taux-melange/taux-melange-detail.component';
import { TauxMelangeService } from '../../../../../../main/webapp/app/entities/taux-melange/taux-melange.service';
import { TauxMelange } from '../../../../../../main/webapp/app/entities/taux-melange/taux-melange.model';

describe('Component Tests', () => {

    describe('TauxMelange Management Detail Component', () => {
        let comp: TauxMelangeDetailComponent;
        let fixture: ComponentFixture<TauxMelangeDetailComponent>;
        let service: TauxMelangeService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EpetrolstationbackendTestModule],
                declarations: [TauxMelangeDetailComponent],
                providers: [
                    TauxMelangeService
                ]
            })
            .overrideTemplate(TauxMelangeDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TauxMelangeDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TauxMelangeService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new TauxMelange(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.tauxMelange).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
