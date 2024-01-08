import {ComponentFixture, TestBed} from '@angular/core/testing';

import {SaveNewSupplierComponent} from './save-new-supplier.component';

describe('SaveNewSupplierComponent', () => {
  let component: SaveNewSupplierComponent;
  let fixture: ComponentFixture<SaveNewSupplierComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SaveNewSupplierComponent]
    })
      .compileComponents();

    fixture = TestBed.createComponent(SaveNewSupplierComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
