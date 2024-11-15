import { Injectable } from '@angular/core';
import { Film, FilmService } from './film.service';
import { HttpClient } from '@angular/common/http';
import { Observable, EMPTY  } from 'rxjs';

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

  constructor(private http: HttpClient) {}

  getDettagliCarrello(idC: number, pageNumber: number = 0, s: string, o: string): Observable<DettaglioCarrello[]> {
    return this.http.get<DettaglioCarrello[]>(
      `${this.apiUrl}/dettagli-carrello`, {
        params: {
          idCliente: idC,
          p: pageNumber,
          sortBy: s,
          order: o
        }
      });
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
  
  
  
}
