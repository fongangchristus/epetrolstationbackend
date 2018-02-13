/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { EpetrolstationbackendTestModule } from '../../../test.module';
import { CiterneDialogComponent } from '../../../../../../main/webapp/app/entities/citerne/citerne-dialog.component';
import { CiterneService } from '../../../../../../main/webapp/app/entities/citerne/citerne.service';
import { Citerne } from '../../../../../../main/webapp/app/entities/citerne/citerne.model';
import { CatCarburantService } from '../../../../../../main/webapp/app/entities/cat-carburant';
import { UniteService } from '../../../../../../main/webapp/app/entities/unite';
import { StationService } from '../../../../../../main/webapp/app/entities/station';

describe('Component Tests', () => {

    describe('Citerne Management Dialog Component', () => {
        let comp: CiterneDialogComponent;
        let fixture: ComponentFixture<CiterneDialogComponent>;
        let service: CiterneService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EpetrolstationbackendTestModule],
                declarations: [CiterneDialogComponent],
                providers: [
                    CatCarburantService,
                    UniteService,
                    StationService,
                    CiterneService
                ]
            })
            .overrideTemplate(CiterneDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CiterneDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CiterneService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Citerne(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.citerne = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'citerneListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Citerne();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.citerne = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'citerneListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
