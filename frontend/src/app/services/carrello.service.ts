import { Injectable } from '@angular/core';
import { FilmService } from './film.service';
import { HttpClient } from '@angular/common/http';
import { Observable, EMPTY, tap  } from 'rxjs';
import { OrdiniService } from './ordini.service';
import { OAuthService } from 'angular-oauth2-oidc';

export interface Carrello{
  idCliente: number;
  indirizzo: string;
  idMetodoDiPagamento: number;
}

export interface DettaglioCarrello {
  idDettaglioCarrello: number;
  carrelloId: number;
  filmId: number;
  prezzoUnita: number; 
  quantita: number;
}


@Injectable({
  providedIn: 'root'
})
export class CarrelloService {

  private apiUrl = FilmService.apiUrl + "/carrello";

  private selectedDettaglioCarrello : DettaglioCarrello | null = null;

  private dettagliCarrello: DettaglioCarrello[] = [];

  constructor(
    private http: HttpClient,
    private ordiniService: OrdiniService,
    private oauthService: OAuthService
  ) {}

  getDettagliCarrello(
    pageNumber: number = 0,
    sortBy: string,
    order: string
  ): Observable<DettaglioCarrello[]> {
    console.log(this.oauthService.getAccessToken());
    return this.http.get<DettaglioCarrello[]>(
      `${this.apiUrl}/dettagli-carrello`,
      {
        params: {
          p: pageNumber.toString(),
          sortBy,
          order
        },
        headers: {
          'Authorization':`Bearer ${this.oauthService.getAccessToken()}`
        }
      }
    ).pipe(
      tap(dettagli => {
        this.dettagliCarrello = dettagli;
      })
    );
  }
  

  setSelectedDettaglio(dettaglio: DettaglioCarrello): void {
    this.selectedDettaglioCarrello = dettaglio;
  }

  getSelectedDettaglio(): DettaglioCarrello | null {
    return this.selectedDettaglioCarrello;
  }

  aggiungiAlCarrello(idFilm: number | undefined, quantity: number): Observable<string> {
    if(idFilm){
      return this.http.post<string>(
        `${this.apiUrl}/aggiungi`, 
        null,
        {
          params: {
            idFilm: idFilm,
            quantity: quantity
          },
          headers: {
            'Authorization':`Bearer ${this.oauthService.getAccessToken()}`
          }
        }
      );
    }
    return EMPTY;
  }

  acquistaDalCarrello(indirizzo: string, numero: number): Observable<String> {
    return this.ordiniService.acquistaDalCarrello(
      indirizzo,
      numero,
      this.dettagliCarrello
    );
  }
  
}
