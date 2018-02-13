/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { EpetrolstationbackendTestModule } from '../../../test.module';
import { ModeReglementDialogComponent } from '../../../../../../main/webapp/app/entities/mode-reglement/mode-reglement-dialog.component';
import { ModeReglementService } from '../../../../../../main/webapp/app/entities/mode-reglement/mode-reglement.service';
import { ModeReglement } from '../../../../../../main/webapp/app/entities/mode-reglement/mode-reglement.model';

describe('Component Tests', () => {

    describe('ModeReglement Management Dialog Component', () => {
        let comp: ModeReglementDialogComponent;
        let fixture: ComponentFixture<ModeReglementDialogComponent>;
        let service: ModeReglementService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EpetrolstationbackendTestModule],
                declarations: [ModeReglementDialogComponent],
                providers: [
                    ModeReglementService
                ]
            })
            .overrideTemplate(ModeReglementDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ModeReglementDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ModeReglementService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new ModeReglement(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.modeReglement = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'modeReglementListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new ModeReglement();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.modeReglement = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'modeReglementListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
