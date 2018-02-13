/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { EpetrolstationbackendTestModule } from '../../../test.module';
import { EntreeCarburantDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/entree-carburant/entree-carburant-delete-dialog.component';
import { EntreeCarburantService } from '../../../../../../main/webapp/app/entities/entree-carburant/entree-carburant.service';

describe('Component Tests', () => {

    describe('EntreeCarburant Management Delete Component', () => {
        let comp: EntreeCarburantDeleteDialogComponent;
        let fixture: ComponentFixture<EntreeCarburantDeleteDialogComponent>;
        let service: EntreeCarburantService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EpetrolstationbackendTestModule],
                declarations: [EntreeCarburantDeleteDialogComponent],
                providers: [
                    EntreeCarburantService
                ]
            })
            .overrideTemplate(EntreeCarburantDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(EntreeCarburantDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EntreeCarburantService);
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
