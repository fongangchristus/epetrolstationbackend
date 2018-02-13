/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { EpetrolstationbackendTestModule } from '../../../test.module';
import { CiterneComponent } from '../../../../../../main/webapp/app/entities/citerne/citerne.component';
import { CiterneService } from '../../../../../../main/webapp/app/entities/citerne/citerne.service';
import { Citerne } from '../../../../../../main/webapp/app/entities/citerne/citerne.model';

describe('Component Tests', () => {

    describe('Citerne Management Component', () => {
        let comp: CiterneComponent;
        let fixture: ComponentFixture<CiterneComponent>;
        let service: CiterneService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EpetrolstationbackendTestModule],
                declarations: [CiterneComponent],
                providers: [
                    CiterneService
                ]
            })
            .overrideTemplate(CiterneComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CiterneComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CiterneService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Citerne(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.citernes[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
