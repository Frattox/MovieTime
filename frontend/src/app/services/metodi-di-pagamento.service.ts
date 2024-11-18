import { Injectable } from '@angular/core';
import { FilmService } from './film.service';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { OAuthService } from 'angular-oauth2-oidc';

export interface MetodoDiPagamento {
  tipo: string;
  numero: number;
}

@Injectable({
  providedIn: 'root'
})
export class MetodiDiPagamentoService {

  private apiUrl = FilmService.apiUrl + "/cliente/metodi-pagamento";

  private selectedMetodoDiPagamento?: MetodoDiPagamento;

  constructor(
    private http: HttpClient,
    private oauthService: OAuthService
  ) {}

  getMetodiPagamento(): Observable<MetodoDiPagamento[]> {
    this.selectedMetodoDiPagamento = undefined;
    return this.http.get<MetodoDiPagamento[]>(this.apiUrl,{
      headers: {
        'Authorization':`Bearer ${this.oauthService.getAccessToken()}`
      }
    }
    );
  }

  setSelectedMetodoDiPagamento(metodo: MetodoDiPagamento) {
    this.selectedMetodoDiPagamento = metodo;
  }

  getSelectedMetodoDiPagamento(): MetodoDiPagamento | undefined {
    return this.selectedMetodoDiPagamento;
  }

  addMetodoPagamento(): Observable<MetodoDiPagamento> {
    const ret: Observable<MetodoDiPagamento> = this.http.post<MetodoDiPagamento>(
      this.apiUrl,
      null,
      {
      params: {
        numero: this.selectedMetodoDiPagamento!.numero,
        tipo: this.selectedMetodoDiPagamento!.tipo
      },
      headers: {
        'Authorization':`Bearer ${this.oauthService.getAccessToken()}`
      }
    });
    ret.subscribe();
    return ret;
  }

}