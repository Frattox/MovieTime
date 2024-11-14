import { Injectable } from '@angular/core';
import { FilmService } from './film.service';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

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

  getDettagliCarrello(idCliente: number, pageNumber: number = 0, sortBy: string, order: string): Observable<DettaglioCarrello[]> {
    return this.http.get<DettaglioCarrello[]>(
      `${this.apiUrl}/dettagli-carrello`, {
        params: {
          idCliente: idCliente,
          p: pageNumber,
          sortBy: sortBy,
          order: order
        }
      });
  }

  setSelectedDettaglio(film: DettaglioCarrello): void {
    this.selectedDettaglioCarrello = film;
  }

  getSelectedDettaglio(): DettaglioCarrello | null {
    return this.selectedDettaglioCarrello;
  }
}
