/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { EpetrolstationbackendTestModule } from '../../../test.module';
import { SortieCarburantDetailComponent } from '../../../../../../main/webapp/app/entities/sortie-carburant/sortie-carburant-detail.component';
import { SortieCarburantService } from '../../../../../../main/webapp/app/entities/sortie-carburant/sortie-carburant.service';
import { SortieCarburant } from '../../../../../../main/webapp/app/entities/sortie-carburant/sortie-carburant.model';

describe('Component Tests', () => {

    describe('SortieCarburant Management Detail Component', () => {
        let comp: SortieCarburantDetailComponent;
        let fixture: ComponentFixture<SortieCarburantDetailComponent>;
        let service: SortieCarburantService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EpetrolstationbackendTestModule],
                declarations: [SortieCarburantDetailComponent],
                providers: [
                    SortieCarburantService
                ]
            })
            .overrideTemplate(SortieCarburantDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SortieCarburantDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SortieCarburantService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new SortieCarburant(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.sortieCarburant).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
