/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { EpetrolstationbackendTestModule } from '../../../test.module';
import { CatCarburantDetailComponent } from '../../../../../../main/webapp/app/entities/cat-carburant/cat-carburant-detail.component';
import { CatCarburantService } from '../../../../../../main/webapp/app/entities/cat-carburant/cat-carburant.service';
import { CatCarburant } from '../../../../../../main/webapp/app/entities/cat-carburant/cat-carburant.model';

describe('Component Tests', () => {

    describe('CatCarburant Management Detail Component', () => {
        let comp: CatCarburantDetailComponent;
        let fixture: ComponentFixture<CatCarburantDetailComponent>;
        let service: CatCarburantService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EpetrolstationbackendTestModule],
                declarations: [CatCarburantDetailComponent],
                providers: [
                    CatCarburantService
                ]
            })
            .overrideTemplate(CatCarburantDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CatCarburantDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CatCarburantService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new CatCarburant(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.catCarburant).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
