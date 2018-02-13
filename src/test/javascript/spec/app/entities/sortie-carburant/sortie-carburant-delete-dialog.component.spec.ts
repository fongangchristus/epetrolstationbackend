/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { EpetrolstationbackendTestModule } from '../../../test.module';
import { SortieCarburantDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/sortie-carburant/sortie-carburant-delete-dialog.component';
import { SortieCarburantService } from '../../../../../../main/webapp/app/entities/sortie-carburant/sortie-carburant.service';

describe('Component Tests', () => {

    describe('SortieCarburant Management Delete Component', () => {
        let comp: SortieCarburantDeleteDialogComponent;
        let fixture: ComponentFixture<SortieCarburantDeleteDialogComponent>;
        let service: SortieCarburantService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EpetrolstationbackendTestModule],
                declarations: [SortieCarburantDeleteDialogComponent],
                providers: [
                    SortieCarburantService
                ]
            })
            .overrideTemplate(SortieCarburantDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SortieCarburantDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SortieCarburantService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'delete').and.returnValue(Observable.of({}));

                        // WHEN
                        comp.confirmDelete(123);
                        tick();

                        // THEN
                        expect(service.delete).toHaveBeenCalledWith(123);
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
