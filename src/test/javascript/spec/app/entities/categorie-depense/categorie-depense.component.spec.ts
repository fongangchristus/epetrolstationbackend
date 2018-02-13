/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { EpetrolstationbackendTestModule } from '../../../test.module';
import { CategorieDepenseComponent } from '../../../../../../main/webapp/app/entities/categorie-depense/categorie-depense.component';
import { CategorieDepenseService } from '../../../../../../main/webapp/app/entities/categorie-depense/categorie-depense.service';
import { CategorieDepense } from '../../../../../../main/webapp/app/entities/categorie-depense/categorie-depense.model';

describe('Component Tests', () => {

    describe('CategorieDepense Management Component', () => {
        let comp: CategorieDepenseComponent;
        let fixture: ComponentFixture<CategorieDepenseComponent>;
        let service: CategorieDepenseService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EpetrolstationbackendTestModule],
                declarations: [CategorieDepenseComponent],
                providers: [
                    CategorieDepenseService
                ]
            })
            .overrideTemplate(CategorieDepenseComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CategorieDepenseComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CategorieDepenseService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new CategorieDepense(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.categorieDepenses[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
