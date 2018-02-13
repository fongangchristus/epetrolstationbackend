/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { EpetrolstationbackendTestModule } from '../../../test.module';
import { EntreeCarburantComponent } from '../../../../../../main/webapp/app/entities/entree-carburant/entree-carburant.component';
import { EntreeCarburantService } from '../../../../../../main/webapp/app/entities/entree-carburant/entree-carburant.service';
import { EntreeCarburant } from '../../../../../../main/webapp/app/entities/entree-carburant/entree-carburant.model';

describe('Component Tests', () => {

    describe('EntreeCarburant Management Component', () => {
        let comp: EntreeCarburantComponent;
        let fixture: ComponentFixture<EntreeCarburantComponent>;
        let service: EntreeCarburantService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EpetrolstationbackendTestModule],
                declarations: [EntreeCarburantComponent],
                providers: [
                    EntreeCarburantService
                ]
            })
            .overrideTemplate(EntreeCarburantComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(EntreeCarburantComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EntreeCarburantService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new EntreeCarburant(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.entreeCarburants[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
