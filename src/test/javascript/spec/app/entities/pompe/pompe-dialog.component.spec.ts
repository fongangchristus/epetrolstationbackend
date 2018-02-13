/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { EpetrolstationbackendTestModule } from '../../../test.module';
import { PompeDialogComponent } from '../../../../../../main/webapp/app/entities/pompe/pompe-dialog.component';
import { PompeService } from '../../../../../../main/webapp/app/entities/pompe/pompe.service';
import { Pompe } from '../../../../../../main/webapp/app/entities/pompe/pompe.model';
import { CiterneService } from '../../../../../../main/webapp/app/entities/citerne';
import { ReservoirService } from '../../../../../../main/webapp/app/entities/reservoir';
import { TauxMelangeService } from '../../../../../../main/webapp/app/entities/taux-melange';
import { CatCarburantService } from '../../../../../../main/webapp/app/entities/cat-carburant';

describe('Component Tests', () => {

    describe('Pompe Management Dialog Component', () => {
        let comp: PompeDialogComponent;
        let fixture: ComponentFixture<PompeDialogComponent>;
        let service: PompeService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EpetrolstationbackendTestModule],
                declarations: [PompeDialogComponent],
                providers: [
                    CiterneService,
                    ReservoirService,
                    TauxMelangeService,
                    CatCarburantService,
                    PompeService
                ]
            })
            .overrideTemplate(PompeDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PompeDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PompeService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Pompe(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.pompe = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'pompeListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Pompe();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.pompe = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'pompeListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
