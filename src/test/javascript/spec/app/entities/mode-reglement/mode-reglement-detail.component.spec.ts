/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { EpetrolstationbackendTestModule } from '../../../test.module';
import { ModeReglementDetailComponent } from '../../../../../../main/webapp/app/entities/mode-reglement/mode-reglement-detail.component';
import { ModeReglementService } from '../../../../../../main/webapp/app/entities/mode-reglement/mode-reglement.service';
import { ModeReglement } from '../../../../../../main/webapp/app/entities/mode-reglement/mode-reglement.model';

describe('Component Tests', () => {

    describe('ModeReglement Management Detail Component', () => {
        let comp: ModeReglementDetailComponent;
        let fixture: ComponentFixture<ModeReglementDetailComponent>;
        let service: ModeReglementService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EpetrolstationbackendTestModule],
                declarations: [ModeReglementDetailComponent],
                providers: [
                    ModeReglementService
                ]
            })
            .overrideTemplate(ModeReglementDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ModeReglementDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ModeReglementService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new ModeReglement(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.modeReglement).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
