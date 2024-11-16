import { TestBed } from '@angular/core/testing';

import { MetodiDiPagamentoService } from './metodi-di-pagamento.service';

describe('MetodiDiPagamentoService', () => {
  let service: MetodiDiPagamentoService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(MetodiDiPagamentoService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
