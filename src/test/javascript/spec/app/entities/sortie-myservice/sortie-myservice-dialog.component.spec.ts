/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { EpetrolstationbackendTestModule } from '../../../test.module';
import { SortieMyserviceDialogComponent } from '../../../../../../main/webapp/app/entities/sortie-myservice/sortie-myservice-dialog.component';
import { SortieMyserviceService } from '../../../../../../main/webapp/app/entities/sortie-myservice/sortie-myservice.service';
import { SortieMyservice } from '../../../../../../main/webapp/app/entities/sortie-myservice/sortie-myservice.model';
import { MyserviceService } from '../../../../../../main/webapp/app/entities/myservice';
import { IntervenantService } from '../../../../../../main/webapp/app/entities/intervenant';
import { ModeReglementService } from '../../../../../../main/webapp/app/entities/mode-reglement';
import { TresorerieService } from '../../../../../../main/webapp/app/entities/tresorerie';

describe('Component Tests', () => {

    describe('SortieMyservice Management Dialog Component', () => {
        let comp: SortieMyserviceDialogComponent;
        let fixture: ComponentFixture<SortieMyserviceDialogComponent>;
        let service: SortieMyserviceService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EpetrolstationbackendTestModule],
                declarations: [SortieMyserviceDialogComponent],
                providers: [
                    MyserviceService,
                    IntervenantService,
                    ModeReglementService,
                    TresorerieService,
                    SortieMyserviceService
                ]
            })
            .overrideTemplate(SortieMyserviceDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SortieMyserviceDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SortieMyserviceService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new SortieMyservice(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.sortieMyservice = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'sortieMyserviceListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new SortieMyservice();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.sortieMyservice = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'sortieMyserviceListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
