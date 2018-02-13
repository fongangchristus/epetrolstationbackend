/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { EpetrolstationbackendTestModule } from '../../../test.module';
import { UniteComponent } from '../../../../../../main/webapp/app/entities/unite/unite.component';
import { UniteService } from '../../../../../../main/webapp/app/entities/unite/unite.service';
import { Unite } from '../../../../../../main/webapp/app/entities/unite/unite.model';

describe('Component Tests', () => {

    describe('Unite Management Component', () => {
        let comp: UniteComponent;
        let fixture: ComponentFixture<UniteComponent>;
        let service: UniteService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EpetrolstationbackendTestModule],
                declarations: [UniteComponent],
                providers: [
                    UniteService
                ]
            })
            .overrideTemplate(UniteComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(UniteComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(UniteService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Unite(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.unites[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
