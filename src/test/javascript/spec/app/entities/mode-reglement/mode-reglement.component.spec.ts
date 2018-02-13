/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { EpetrolstationbackendTestModule } from '../../../test.module';
import { ModeReglementComponent } from '../../../../../../main/webapp/app/entities/mode-reglement/mode-reglement.component';
import { ModeReglementService } from '../../../../../../main/webapp/app/entities/mode-reglement/mode-reglement.service';
import { ModeReglement } from '../../../../../../main/webapp/app/entities/mode-reglement/mode-reglement.model';

describe('Component Tests', () => {

    describe('ModeReglement Management Component', () => {
        let comp: ModeReglementComponent;
        let fixture: ComponentFixture<ModeReglementComponent>;
        let service: ModeReglementService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EpetrolstationbackendTestModule],
                declarations: [ModeReglementComponent],
                providers: [
                    ModeReglementService
                ]
            })
            .overrideTemplate(ModeReglementComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ModeReglementComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ModeReglementService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new ModeReglement(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.modeReglements[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
