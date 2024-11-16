import { Injectable } from '@angular/core';
import { FilmService } from './film.service';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface MetodoDiPagamento {
  idMetodoDiPagamento: number;
  idCliente: number;
  tipo: string;
  numero: number;
}

@Injectable({
  providedIn: 'root'
})
export class MetodiDiPagamentoService {

  private apiUrl = FilmService.apiUrl + "/cliente/metodi-pagamento";

  private selectedMetodoDiPagamento: MetodoDiPagamento | null = null;

  constructor(private http: HttpClient) {}

  getMetodiPagamento(idC: number): Observable<MetodoDiPagamento[]> {
    return this.http.get<MetodoDiPagamento[]>(this.apiUrl, {
      params: {
        idCliente: idC
      }
    });
  }

  setSelectedMetodoDiPagamento(metodo: MetodoDiPagamento) {
    this.selectedMetodoDiPagamento = metodo;
  }

  getSelectedMetodoDiPagamento(): MetodoDiPagamento | null {
    return this.selectedMetodoDiPagamento;
  }
}
