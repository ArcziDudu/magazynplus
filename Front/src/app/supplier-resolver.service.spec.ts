import { TestBed } from '@angular/core/testing';

import { SupplierResolverService } from './supplier-resolver.service';

describe('SupplierResolverService', () => {
  let service: SupplierResolverService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SupplierResolverService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
