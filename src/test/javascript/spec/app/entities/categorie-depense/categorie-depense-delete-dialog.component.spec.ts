/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { EpetrolstationbackendTestModule } from '../../../test.module';
import { CategorieDepenseDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/categorie-depense/categorie-depense-delete-dialog.component';
import { CategorieDepenseService } from '../../../../../../main/webapp/app/entities/categorie-depense/categorie-depense.service';

describe('Component Tests', () => {

    describe('CategorieDepense Management Delete Component', () => {
        let comp: CategorieDepenseDeleteDialogComponent;
        let fixture: ComponentFixture<CategorieDepenseDeleteDialogComponent>;
        let service: CategorieDepenseService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EpetrolstationbackendTestModule],
                declarations: [CategorieDepenseDeleteDialogComponent],
                providers: [
                    CategorieDepenseService
                ]
            })
            .overrideTemplate(CategorieDepenseDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CategorieDepenseDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CategorieDepenseService);
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
