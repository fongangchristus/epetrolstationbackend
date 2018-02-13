/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { EpetrolstationbackendTestModule } from '../../../test.module';
import { CategorieDepenseDetailComponent } from '../../../../../../main/webapp/app/entities/categorie-depense/categorie-depense-detail.component';
import { CategorieDepenseService } from '../../../../../../main/webapp/app/entities/categorie-depense/categorie-depense.service';
import { CategorieDepense } from '../../../../../../main/webapp/app/entities/categorie-depense/categorie-depense.model';

describe('Component Tests', () => {

    describe('CategorieDepense Management Detail Component', () => {
        let comp: CategorieDepenseDetailComponent;
        let fixture: ComponentFixture<CategorieDepenseDetailComponent>;
        let service: CategorieDepenseService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EpetrolstationbackendTestModule],
                declarations: [CategorieDepenseDetailComponent],
                providers: [
                    CategorieDepenseService
                ]
            })
            .overrideTemplate(CategorieDepenseDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CategorieDepenseDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CategorieDepenseService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new CategorieDepense(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.categorieDepense).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
