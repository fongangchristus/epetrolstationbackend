/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { EpetrolstationbackendTestModule } from '../../../test.module';
import { FraisRecueComponent } from '../../../../../../main/webapp/app/entities/frais-recue/frais-recue.component';
import { FraisRecueService } from '../../../../../../main/webapp/app/entities/frais-recue/frais-recue.service';
import { FraisRecue } from '../../../../../../main/webapp/app/entities/frais-recue/frais-recue.model';

describe('Component Tests', () => {

    describe('FraisRecue Management Component', () => {
        let comp: FraisRecueComponent;
        let fixture: ComponentFixture<FraisRecueComponent>;
        let service: FraisRecueService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EpetrolstationbackendTestModule],
                declarations: [FraisRecueComponent],
                providers: [
                    FraisRecueService
                ]
            })
            .overrideTemplate(FraisRecueComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(FraisRecueComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FraisRecueService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new FraisRecue(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.fraisRecues[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
