import { Injectable } from '@angular/core';
import { FilmService } from './film.service';
import { HttpClient } from '@angular/common/http';
import { catchError, Observable, throwError } from 'rxjs';

export interface MetodoDiPagamento {
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

  addMetodoPagamento(): Observable<MetodoDiPagamento> {
    const ret: Observable<MetodoDiPagamento> = this.http.post<MetodoDiPagamento>(this.apiUrl,null,{
      params: {
        idCliente: this.selectedMetodoDiPagamento!.idCliente,
        numero: this.selectedMetodoDiPagamento!.numero,
        tipo: this.selectedMetodoDiPagamento!.tipo
      }
    });
    this.selectedMetodoDiPagamento = null;
    return ret;
  }

}