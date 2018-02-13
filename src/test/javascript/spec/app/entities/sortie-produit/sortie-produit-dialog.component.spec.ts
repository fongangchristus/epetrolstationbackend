/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { EpetrolstationbackendTestModule } from '../../../test.module';
import { SortieProduitDialogComponent } from '../../../../../../main/webapp/app/entities/sortie-produit/sortie-produit-dialog.component';
import { SortieProduitService } from '../../../../../../main/webapp/app/entities/sortie-produit/sortie-produit.service';
import { SortieProduit } from '../../../../../../main/webapp/app/entities/sortie-produit/sortie-produit.model';
import { ModeReglementService } from '../../../../../../main/webapp/app/entities/mode-reglement';
import { IntervenantService } from '../../../../../../main/webapp/app/entities/intervenant';
import { TresorerieService } from '../../../../../../main/webapp/app/entities/tresorerie';
import { ProduitService } from '../../../../../../main/webapp/app/entities/produit';

describe('Component Tests', () => {

    describe('SortieProduit Management Dialog Component', () => {
        let comp: SortieProduitDialogComponent;
        let fixture: ComponentFixture<SortieProduitDialogComponent>;
        let service: SortieProduitService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EpetrolstationbackendTestModule],
                declarations: [SortieProduitDialogComponent],
                providers: [
                    ModeReglementService,
                    IntervenantService,
                    TresorerieService,
                    ProduitService,
                    SortieProduitService
                ]
            })
            .overrideTemplate(SortieProduitDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SortieProduitDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SortieProduitService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new SortieProduit(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.sortieProduit = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'sortieProduitListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new SortieProduit();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.sortieProduit = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'sortieProduitListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
