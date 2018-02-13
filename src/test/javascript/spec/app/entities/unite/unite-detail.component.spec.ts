/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { EpetrolstationbackendTestModule } from '../../../test.module';
import { UniteDetailComponent } from '../../../../../../main/webapp/app/entities/unite/unite-detail.component';
import { UniteService } from '../../../../../../main/webapp/app/entities/unite/unite.service';
import { Unite } from '../../../../../../main/webapp/app/entities/unite/unite.model';

describe('Component Tests', () => {

    describe('Unite Management Detail Component', () => {
        let comp: UniteDetailComponent;
        let fixture: ComponentFixture<UniteDetailComponent>;
        let service: UniteService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EpetrolstationbackendTestModule],
                declarations: [UniteDetailComponent],
                providers: [
                    UniteService
                ]
            })
            .overrideTemplate(UniteDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(UniteDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(UniteService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Unite(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.unite).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
