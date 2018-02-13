/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { EpetrolstationbackendTestModule } from '../../../test.module';
import { EntreeCiterneDialogComponent } from '../../../../../../main/webapp/app/entities/entree-citerne/entree-citerne-dialog.component';
import { EntreeCiterneService } from '../../../../../../main/webapp/app/entities/entree-citerne/entree-citerne.service';
import { EntreeCiterne } from '../../../../../../main/webapp/app/entities/entree-citerne/entree-citerne.model';
import { CiterneService } from '../../../../../../main/webapp/app/entities/citerne';
import { UniteService } from '../../../../../../main/webapp/app/entities/unite';
import { IntervenantService } from '../../../../../../main/webapp/app/entities/intervenant';

describe('Component Tests', () => {

    describe('EntreeCiterne Management Dialog Component', () => {
        let comp: EntreeCiterneDialogComponent;
        let fixture: ComponentFixture<EntreeCiterneDialogComponent>;
        let service: EntreeCiterneService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EpetrolstationbackendTestModule],
                declarations: [EntreeCiterneDialogComponent],
                providers: [
                    CiterneService,
                    UniteService,
                    IntervenantService,
                    EntreeCiterneService
                ]
            })
            .overrideTemplate(EntreeCiterneDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(EntreeCiterneDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EntreeCiterneService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new EntreeCiterne(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.entreeCiterne = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'entreeCiterneListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new EntreeCiterne();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.entreeCiterne = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'entreeCiterneListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
