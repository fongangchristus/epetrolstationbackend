/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { EpetrolstationbackendTestModule } from '../../../test.module';
import { EntreeProduitComponent } from '../../../../../../main/webapp/app/entities/entree-produit/entree-produit.component';
import { EntreeProduitService } from '../../../../../../main/webapp/app/entities/entree-produit/entree-produit.service';
import { EntreeProduit } from '../../../../../../main/webapp/app/entities/entree-produit/entree-produit.model';

describe('Component Tests', () => {

    describe('EntreeProduit Management Component', () => {
        let comp: EntreeProduitComponent;
        let fixture: ComponentFixture<EntreeProduitComponent>;
        let service: EntreeProduitService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EpetrolstationbackendTestModule],
                declarations: [EntreeProduitComponent],
                providers: [
                    EntreeProduitService
                ]
            })
            .overrideTemplate(EntreeProduitComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(EntreeProduitComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EntreeProduitService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new EntreeProduit(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.entreeProduits[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
