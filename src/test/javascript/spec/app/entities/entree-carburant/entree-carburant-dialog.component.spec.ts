/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { EpetrolstationbackendTestModule } from '../../../test.module';
import { EntreeCarburantDialogComponent } from '../../../../../../main/webapp/app/entities/entree-carburant/entree-carburant-dialog.component';
import { EntreeCarburantService } from '../../../../../../main/webapp/app/entities/entree-carburant/entree-carburant.service';
import { EntreeCarburant } from '../../../../../../main/webapp/app/entities/entree-carburant/entree-carburant.model';
import { ModeReglementService } from '../../../../../../main/webapp/app/entities/mode-reglement';
import { CarburantService } from '../../../../../../main/webapp/app/entities/carburant';
import { TresorerieService } from '../../../../../../main/webapp/app/entities/tresorerie';

describe('Component Tests', () => {

    describe('EntreeCarburant Management Dialog Component', () => {
        let comp: EntreeCarburantDialogComponent;
        let fixture: ComponentFixture<EntreeCarburantDialogComponent>;
        let service: EntreeCarburantService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EpetrolstationbackendTestModule],
                declarations: [EntreeCarburantDialogComponent],
                providers: [
                    ModeReglementService,
                    CarburantService,
                    TresorerieService,
                    EntreeCarburantService
                ]
            })
            .overrideTemplate(EntreeCarburantDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(EntreeCarburantDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EntreeCarburantService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new EntreeCarburant(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.entreeCarburant = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'entreeCarburantListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new EntreeCarburant();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.entreeCarburant = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'entreeCarburantListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
