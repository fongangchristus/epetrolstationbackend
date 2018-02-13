/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { EpetrolstationbackendTestModule } from '../../../test.module';
import { PompeComponent } from '../../../../../../main/webapp/app/entities/pompe/pompe.component';
import { PompeService } from '../../../../../../main/webapp/app/entities/pompe/pompe.service';
import { Pompe } from '../../../../../../main/webapp/app/entities/pompe/pompe.model';

describe('Component Tests', () => {

    describe('Pompe Management Component', () => {
        let comp: PompeComponent;
        let fixture: ComponentFixture<PompeComponent>;
        let service: PompeService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EpetrolstationbackendTestModule],
                declarations: [PompeComponent],
                providers: [
                    PompeService
                ]
            })
            .overrideTemplate(PompeComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PompeComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PompeService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Pompe(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.pompes[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
