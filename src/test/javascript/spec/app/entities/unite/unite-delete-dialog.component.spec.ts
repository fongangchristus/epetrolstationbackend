/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { EpetrolstationbackendTestModule } from '../../../test.module';
import { UniteDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/unite/unite-delete-dialog.component';
import { UniteService } from '../../../../../../main/webapp/app/entities/unite/unite.service';

describe('Component Tests', () => {

    describe('Unite Management Delete Component', () => {
        let comp: UniteDeleteDialogComponent;
        let fixture: ComponentFixture<UniteDeleteDialogComponent>;
        let service: UniteService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EpetrolstationbackendTestModule],
                declarations: [UniteDeleteDialogComponent],
                providers: [
                    UniteService
                ]
            })
            .overrideTemplate(UniteDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(UniteDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(UniteService);
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
