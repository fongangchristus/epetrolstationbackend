/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { EpetrolstationbackendTestModule } from '../../../test.module';
import { SortieCarburantDialogComponent } from '../../../../../../main/webapp/app/entities/sortie-carburant/sortie-carburant-dialog.component';
import { SortieCarburantService } from '../../../../../../main/webapp/app/entities/sortie-carburant/sortie-carburant.service';
import { SortieCarburant } from '../../../../../../main/webapp/app/entities/sortie-carburant/sortie-carburant.model';
import { IntervenantService } from '../../../../../../main/webapp/app/entities/intervenant';
import { CarburantService } from '../../../../../../main/webapp/app/entities/carburant';
import { PompeService } from '../../../../../../main/webapp/app/entities/pompe';
import { ModeReglementService } from '../../../../../../main/webapp/app/entities/mode-reglement';
import { TresorerieService } from '../../../../../../main/webapp/app/entities/tresorerie';

describe('Component Tests', () => {

    describe('SortieCarburant Management Dialog Component', () => {
        let comp: SortieCarburantDialogComponent;
        let fixture: ComponentFixture<SortieCarburantDialogComponent>;
        let service: SortieCarburantService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EpetrolstationbackendTestModule],
                declarations: [SortieCarburantDialogComponent],
                providers: [
                    IntervenantService,
                    CarburantService,
                    PompeService,
                    ModeReglementService,
                    TresorerieService,
                    SortieCarburantService
                ]
            })
            .overrideTemplate(SortieCarburantDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SortieCarburantDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SortieCarburantService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new SortieCarburant(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.sortieCarburant = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'sortieCarburantListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new SortieCarburant();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.sortieCarburant = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'sortieCarburantListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
