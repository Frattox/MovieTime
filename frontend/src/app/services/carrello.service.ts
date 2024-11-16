import { Injectable } from '@angular/core';
import { Film, FilmService } from './film.service';
import { HttpClient } from '@angular/common/http';
import { Observable, EMPTY, tap  } from 'rxjs';
import { OrdiniService } from './ordini.service';

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

  private idC = 0;

  private selectedDettaglioCarrello : DettaglioCarrello | null = null;

  private dettagliCarrello: DettaglioCarrello[] = [];

  constructor(private http: HttpClient, private ordiniService: OrdiniService) {}

  getDettagliCarrello(
    idC: number,
    pageNumber: number = 0,
    sortBy: string,
    order: string
  ): Observable<DettaglioCarrello[]> {
    this.idC = idC;
    return this.http.get<DettaglioCarrello[]>(
      `${this.apiUrl}/dettagli-carrello`,
      {
        params: {
          idCliente: idC.toString(),
          p: pageNumber.toString(),
          sortBy,
          order
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

  aggiungiAlCarrello(idCliente: number, idFilm: number | undefined, quantity: number): Observable<string> {
    if(idFilm){
      return this.http.post<string>(
        `${this.apiUrl}/aggiungi`, 
        null,
        {
          params: {
            idCliente: idCliente,
            idFilm: idFilm,
            quantity: quantity
          }
        }
      );
    }
    return EMPTY;
  }

  acquistaDalCarrello(indirizzo: string, numero: number): Observable<String> {
    return this.ordiniService.acquistaDalCarrello(
      indirizzo,
      this.idC,
      numero,
      this.dettagliCarrello
    );
  }
  
}
