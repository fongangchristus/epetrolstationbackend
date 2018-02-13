/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { EpetrolstationbackendTestModule } from '../../../test.module';
import { TresorerieDetailComponent } from '../../../../../../main/webapp/app/entities/tresorerie/tresorerie-detail.component';
import { TresorerieService } from '../../../../../../main/webapp/app/entities/tresorerie/tresorerie.service';
import { Tresorerie } from '../../../../../../main/webapp/app/entities/tresorerie/tresorerie.model';

describe('Component Tests', () => {

    describe('Tresorerie Management Detail Component', () => {
        let comp: TresorerieDetailComponent;
        let fixture: ComponentFixture<TresorerieDetailComponent>;
        let service: TresorerieService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EpetrolstationbackendTestModule],
                declarations: [TresorerieDetailComponent],
                providers: [
                    TresorerieService
                ]
            })
            .overrideTemplate(TresorerieDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TresorerieDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TresorerieService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Tresorerie(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.tresorerie).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
