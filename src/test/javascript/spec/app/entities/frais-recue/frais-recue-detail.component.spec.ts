/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { EpetrolstationbackendTestModule } from '../../../test.module';
import { FraisRecueDetailComponent } from '../../../../../../main/webapp/app/entities/frais-recue/frais-recue-detail.component';
import { FraisRecueService } from '../../../../../../main/webapp/app/entities/frais-recue/frais-recue.service';
import { FraisRecue } from '../../../../../../main/webapp/app/entities/frais-recue/frais-recue.model';

describe('Component Tests', () => {

    describe('FraisRecue Management Detail Component', () => {
        let comp: FraisRecueDetailComponent;
        let fixture: ComponentFixture<FraisRecueDetailComponent>;
        let service: FraisRecueService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EpetrolstationbackendTestModule],
                declarations: [FraisRecueDetailComponent],
                providers: [
                    FraisRecueService
                ]
            })
            .overrideTemplate(FraisRecueDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(FraisRecueDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FraisRecueService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new FraisRecue(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.fraisRecue).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
