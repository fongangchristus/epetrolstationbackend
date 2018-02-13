/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { EpetrolstationbackendTestModule } from '../../../test.module';
import { EntreeCiterneComponent } from '../../../../../../main/webapp/app/entities/entree-citerne/entree-citerne.component';
import { EntreeCiterneService } from '../../../../../../main/webapp/app/entities/entree-citerne/entree-citerne.service';
import { EntreeCiterne } from '../../../../../../main/webapp/app/entities/entree-citerne/entree-citerne.model';

describe('Component Tests', () => {

    describe('EntreeCiterne Management Component', () => {
        let comp: EntreeCiterneComponent;
        let fixture: ComponentFixture<EntreeCiterneComponent>;
        let service: EntreeCiterneService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EpetrolstationbackendTestModule],
                declarations: [EntreeCiterneComponent],
                providers: [
                    EntreeCiterneService
                ]
            })
            .overrideTemplate(EntreeCiterneComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(EntreeCiterneComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EntreeCiterneService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new EntreeCiterne(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.entreeCiternes[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
