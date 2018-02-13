/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { EpetrolstationbackendTestModule } from '../../../test.module';
import { ProduitDetailComponent } from '../../../../../../main/webapp/app/entities/produit/produit-detail.component';
import { ProduitService } from '../../../../../../main/webapp/app/entities/produit/produit.service';
import { Produit } from '../../../../../../main/webapp/app/entities/produit/produit.model';

describe('Component Tests', () => {

    describe('Produit Management Detail Component', () => {
        let comp: ProduitDetailComponent;
        let fixture: ComponentFixture<ProduitDetailComponent>;
        let service: ProduitService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EpetrolstationbackendTestModule],
                declarations: [ProduitDetailComponent],
                providers: [
                    ProduitService
                ]
            })
            .overrideTemplate(ProduitDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ProduitDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ProduitService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Produit(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.produit).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
