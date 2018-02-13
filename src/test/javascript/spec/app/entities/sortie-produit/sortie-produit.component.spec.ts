/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { EpetrolstationbackendTestModule } from '../../../test.module';
import { SortieProduitComponent } from '../../../../../../main/webapp/app/entities/sortie-produit/sortie-produit.component';
import { SortieProduitService } from '../../../../../../main/webapp/app/entities/sortie-produit/sortie-produit.service';
import { SortieProduit } from '../../../../../../main/webapp/app/entities/sortie-produit/sortie-produit.model';

describe('Component Tests', () => {

    describe('SortieProduit Management Component', () => {
        let comp: SortieProduitComponent;
        let fixture: ComponentFixture<SortieProduitComponent>;
        let service: SortieProduitService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EpetrolstationbackendTestModule],
                declarations: [SortieProduitComponent],
                providers: [
                    SortieProduitService
                ]
            })
            .overrideTemplate(SortieProduitComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SortieProduitComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SortieProduitService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new SortieProduit(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.sortieProduits[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
