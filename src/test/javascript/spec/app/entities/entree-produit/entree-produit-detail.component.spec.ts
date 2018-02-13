/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { EpetrolstationbackendTestModule } from '../../../test.module';
import { EntreeProduitDetailComponent } from '../../../../../../main/webapp/app/entities/entree-produit/entree-produit-detail.component';
import { EntreeProduitService } from '../../../../../../main/webapp/app/entities/entree-produit/entree-produit.service';
import { EntreeProduit } from '../../../../../../main/webapp/app/entities/entree-produit/entree-produit.model';

describe('Component Tests', () => {

    describe('EntreeProduit Management Detail Component', () => {
        let comp: EntreeProduitDetailComponent;
        let fixture: ComponentFixture<EntreeProduitDetailComponent>;
        let service: EntreeProduitService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EpetrolstationbackendTestModule],
                declarations: [EntreeProduitDetailComponent],
                providers: [
                    EntreeProduitService
                ]
            })
            .overrideTemplate(EntreeProduitDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(EntreeProduitDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EntreeProduitService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new EntreeProduit(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.entreeProduit).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
