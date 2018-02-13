/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { EpetrolstationbackendTestModule } from '../../../test.module';
import { ProduitComponent } from '../../../../../../main/webapp/app/entities/produit/produit.component';
import { ProduitService } from '../../../../../../main/webapp/app/entities/produit/produit.service';
import { Produit } from '../../../../../../main/webapp/app/entities/produit/produit.model';

describe('Component Tests', () => {

    describe('Produit Management Component', () => {
        let comp: ProduitComponent;
        let fixture: ComponentFixture<ProduitComponent>;
        let service: ProduitService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EpetrolstationbackendTestModule],
                declarations: [ProduitComponent],
                providers: [
                    ProduitService
                ]
            })
            .overrideTemplate(ProduitComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ProduitComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ProduitService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Produit(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.produits[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
