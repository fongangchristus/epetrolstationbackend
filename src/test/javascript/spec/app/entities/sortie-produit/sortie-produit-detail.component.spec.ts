/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { EpetrolstationbackendTestModule } from '../../../test.module';
import { SortieProduitDetailComponent } from '../../../../../../main/webapp/app/entities/sortie-produit/sortie-produit-detail.component';
import { SortieProduitService } from '../../../../../../main/webapp/app/entities/sortie-produit/sortie-produit.service';
import { SortieProduit } from '../../../../../../main/webapp/app/entities/sortie-produit/sortie-produit.model';

describe('Component Tests', () => {

    describe('SortieProduit Management Detail Component', () => {
        let comp: SortieProduitDetailComponent;
        let fixture: ComponentFixture<SortieProduitDetailComponent>;
        let service: SortieProduitService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EpetrolstationbackendTestModule],
                declarations: [SortieProduitDetailComponent],
                providers: [
                    SortieProduitService
                ]
            })
            .overrideTemplate(SortieProduitDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SortieProduitDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SortieProduitService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new SortieProduit(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.sortieProduit).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
