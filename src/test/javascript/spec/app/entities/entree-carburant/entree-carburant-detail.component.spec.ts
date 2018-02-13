/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { EpetrolstationbackendTestModule } from '../../../test.module';
import { EntreeCarburantDetailComponent } from '../../../../../../main/webapp/app/entities/entree-carburant/entree-carburant-detail.component';
import { EntreeCarburantService } from '../../../../../../main/webapp/app/entities/entree-carburant/entree-carburant.service';
import { EntreeCarburant } from '../../../../../../main/webapp/app/entities/entree-carburant/entree-carburant.model';

describe('Component Tests', () => {

    describe('EntreeCarburant Management Detail Component', () => {
        let comp: EntreeCarburantDetailComponent;
        let fixture: ComponentFixture<EntreeCarburantDetailComponent>;
        let service: EntreeCarburantService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EpetrolstationbackendTestModule],
                declarations: [EntreeCarburantDetailComponent],
                providers: [
                    EntreeCarburantService
                ]
            })
            .overrideTemplate(EntreeCarburantDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(EntreeCarburantDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EntreeCarburantService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new EntreeCarburant(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.entreeCarburant).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
