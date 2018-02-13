/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { EpetrolstationbackendTestModule } from '../../../test.module';
import { TypeTresorerieComponent } from '../../../../../../main/webapp/app/entities/type-tresorerie/type-tresorerie.component';
import { TypeTresorerieService } from '../../../../../../main/webapp/app/entities/type-tresorerie/type-tresorerie.service';
import { TypeTresorerie } from '../../../../../../main/webapp/app/entities/type-tresorerie/type-tresorerie.model';

describe('Component Tests', () => {

    describe('TypeTresorerie Management Component', () => {
        let comp: TypeTresorerieComponent;
        let fixture: ComponentFixture<TypeTresorerieComponent>;
        let service: TypeTresorerieService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EpetrolstationbackendTestModule],
                declarations: [TypeTresorerieComponent],
                providers: [
                    TypeTresorerieService
                ]
            })
            .overrideTemplate(TypeTresorerieComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TypeTresorerieComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TypeTresorerieService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new TypeTresorerie(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.typeTresoreries[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
