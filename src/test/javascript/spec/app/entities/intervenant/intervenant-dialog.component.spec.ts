/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { EpetrolstationbackendTestModule } from '../../../test.module';
import { IntervenantDialogComponent } from '../../../../../../main/webapp/app/entities/intervenant/intervenant-dialog.component';
import { IntervenantService } from '../../../../../../main/webapp/app/entities/intervenant/intervenant.service';
import { Intervenant } from '../../../../../../main/webapp/app/entities/intervenant/intervenant.model';
import { EntreeProduitService } from '../../../../../../main/webapp/app/entities/entree-produit';
import { EntreeCarburantService } from '../../../../../../main/webapp/app/entities/entree-carburant';
import { TypeIntervenantService } from '../../../../../../main/webapp/app/entities/type-intervenant';
import { UserService } from '../../../../../../main/webapp/app/shared';
import { StationService } from '../../../../../../main/webapp/app/entities/station';

describe('Component Tests', () => {

    describe('Intervenant Management Dialog Component', () => {
        let comp: IntervenantDialogComponent;
        let fixture: ComponentFixture<IntervenantDialogComponent>;
        let service: IntervenantService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EpetrolstationbackendTestModule],
                declarations: [IntervenantDialogComponent],
                providers: [
                    EntreeProduitService,
                    EntreeCarburantService,
                    TypeIntervenantService,
                    UserService,
                    StationService,
                    IntervenantService
                ]
            })
            .overrideTemplate(IntervenantDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(IntervenantDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(IntervenantService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Intervenant(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.intervenant = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'intervenantListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Intervenant();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.intervenant = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'intervenantListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
