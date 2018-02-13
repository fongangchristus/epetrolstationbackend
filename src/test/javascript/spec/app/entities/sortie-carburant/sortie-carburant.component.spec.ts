/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { EpetrolstationbackendTestModule } from '../../../test.module';
import { SortieCarburantComponent } from '../../../../../../main/webapp/app/entities/sortie-carburant/sortie-carburant.component';
import { SortieCarburantService } from '../../../../../../main/webapp/app/entities/sortie-carburant/sortie-carburant.service';
import { SortieCarburant } from '../../../../../../main/webapp/app/entities/sortie-carburant/sortie-carburant.model';

describe('Component Tests', () => {

    describe('SortieCarburant Management Component', () => {
        let comp: SortieCarburantComponent;
        let fixture: ComponentFixture<SortieCarburantComponent>;
        let service: SortieCarburantService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EpetrolstationbackendTestModule],
                declarations: [SortieCarburantComponent],
                providers: [
                    SortieCarburantService
                ]
            })
            .overrideTemplate(SortieCarburantComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SortieCarburantComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SortieCarburantService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new SortieCarburant(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.sortieCarburants[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
