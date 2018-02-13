/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { EpetrolstationbackendTestModule } from '../../../test.module';
import { EntreeProduitDialogComponent } from '../../../../../../main/webapp/app/entities/entree-produit/entree-produit-dialog.component';
import { EntreeProduitService } from '../../../../../../main/webapp/app/entities/entree-produit/entree-produit.service';
import { EntreeProduit } from '../../../../../../main/webapp/app/entities/entree-produit/entree-produit.model';
import { ModeReglementService } from '../../../../../../main/webapp/app/entities/mode-reglement';
import { TresorerieService } from '../../../../../../main/webapp/app/entities/tresorerie';
import { ProduitService } from '../../../../../../main/webapp/app/entities/produit';

describe('Component Tests', () => {

    describe('EntreeProduit Management Dialog Component', () => {
        let comp: EntreeProduitDialogComponent;
        let fixture: ComponentFixture<EntreeProduitDialogComponent>;
        let service: EntreeProduitService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EpetrolstationbackendTestModule],
                declarations: [EntreeProduitDialogComponent],
                providers: [
                    ModeReglementService,
                    TresorerieService,
                    ProduitService,
                    EntreeProduitService
                ]
            })
            .overrideTemplate(EntreeProduitDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(EntreeProduitDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EntreeProduitService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new EntreeProduit(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.entreeProduit = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'entreeProduitListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new EntreeProduit();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.entreeProduit = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'entreeProduitListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
