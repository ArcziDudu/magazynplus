import {ComponentFixture, TestBed} from '@angular/core/testing';

import {SaveNewProductComponent} from './save-new-product.component';

describe('SaveNewProductComponent', () => {
  let component: SaveNewProductComponent;
  let fixture: ComponentFixture<SaveNewProductComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SaveNewProductComponent]
    })
      .compileComponents();

    fixture = TestBed.createComponent(SaveNewProductComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
