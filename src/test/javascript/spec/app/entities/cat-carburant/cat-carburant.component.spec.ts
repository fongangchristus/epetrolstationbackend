/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { EpetrolstationbackendTestModule } from '../../../test.module';
import { CatCarburantComponent } from '../../../../../../main/webapp/app/entities/cat-carburant/cat-carburant.component';
import { CatCarburantService } from '../../../../../../main/webapp/app/entities/cat-carburant/cat-carburant.service';
import { CatCarburant } from '../../../../../../main/webapp/app/entities/cat-carburant/cat-carburant.model';

describe('Component Tests', () => {

    describe('CatCarburant Management Component', () => {
        let comp: CatCarburantComponent;
        let fixture: ComponentFixture<CatCarburantComponent>;
        let service: CatCarburantService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EpetrolstationbackendTestModule],
                declarations: [CatCarburantComponent],
                providers: [
                    CatCarburantService
                ]
            })
            .overrideTemplate(CatCarburantComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CatCarburantComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CatCarburantService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new CatCarburant(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.catCarburants[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
