/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { EpetrolstationbackendTestModule } from '../../../test.module';
import { TypeTresorerieDialogComponent } from '../../../../../../main/webapp/app/entities/type-tresorerie/type-tresorerie-dialog.component';
import { TypeTresorerieService } from '../../../../../../main/webapp/app/entities/type-tresorerie/type-tresorerie.service';
import { TypeTresorerie } from '../../../../../../main/webapp/app/entities/type-tresorerie/type-tresorerie.model';

describe('Component Tests', () => {

    describe('TypeTresorerie Management Dialog Component', () => {
        let comp: TypeTresorerieDialogComponent;
        let fixture: ComponentFixture<TypeTresorerieDialogComponent>;
        let service: TypeTresorerieService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EpetrolstationbackendTestModule],
                declarations: [TypeTresorerieDialogComponent],
                providers: [
                    TypeTresorerieService
                ]
            })
            .overrideTemplate(TypeTresorerieDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TypeTresorerieDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TypeTresorerieService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new TypeTresorerie(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.typeTresorerie = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'typeTresorerieListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new TypeTresorerie();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.typeTresorerie = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'typeTresorerieListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
