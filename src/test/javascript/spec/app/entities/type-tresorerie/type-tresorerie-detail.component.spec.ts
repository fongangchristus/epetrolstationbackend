/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { EpetrolstationbackendTestModule } from '../../../test.module';
import { TypeTresorerieDetailComponent } from '../../../../../../main/webapp/app/entities/type-tresorerie/type-tresorerie-detail.component';
import { TypeTresorerieService } from '../../../../../../main/webapp/app/entities/type-tresorerie/type-tresorerie.service';
import { TypeTresorerie } from '../../../../../../main/webapp/app/entities/type-tresorerie/type-tresorerie.model';

describe('Component Tests', () => {

    describe('TypeTresorerie Management Detail Component', () => {
        let comp: TypeTresorerieDetailComponent;
        let fixture: ComponentFixture<TypeTresorerieDetailComponent>;
        let service: TypeTresorerieService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EpetrolstationbackendTestModule],
                declarations: [TypeTresorerieDetailComponent],
                providers: [
                    TypeTresorerieService
                ]
            })
            .overrideTemplate(TypeTresorerieDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TypeTresorerieDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TypeTresorerieService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new TypeTresorerie(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.typeTresorerie).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
