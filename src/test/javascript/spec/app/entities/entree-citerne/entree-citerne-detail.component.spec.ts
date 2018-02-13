/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { EpetrolstationbackendTestModule } from '../../../test.module';
import { EntreeCiterneDetailComponent } from '../../../../../../main/webapp/app/entities/entree-citerne/entree-citerne-detail.component';
import { EntreeCiterneService } from '../../../../../../main/webapp/app/entities/entree-citerne/entree-citerne.service';
import { EntreeCiterne } from '../../../../../../main/webapp/app/entities/entree-citerne/entree-citerne.model';

describe('Component Tests', () => {

    describe('EntreeCiterne Management Detail Component', () => {
        let comp: EntreeCiterneDetailComponent;
        let fixture: ComponentFixture<EntreeCiterneDetailComponent>;
        let service: EntreeCiterneService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EpetrolstationbackendTestModule],
                declarations: [EntreeCiterneDetailComponent],
                providers: [
                    EntreeCiterneService
                ]
            })
            .overrideTemplate(EntreeCiterneDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(EntreeCiterneDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EntreeCiterneService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new EntreeCiterne(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.entreeCiterne).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
