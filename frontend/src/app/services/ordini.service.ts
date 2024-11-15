import { Injectable } from '@angular/core';
import { FilmService } from './film.service';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Ordine{
  idOrdine: number;
  dataOrdine: Date;
  stato: string;
  indirizzo: string;
}

export interface DettaglioOrdine{
  idDettaglioOrdine: number;
  quantita: number;
  prezzoUnita: number;
  filmId: number;
}

@Injectable({
  providedIn: 'root'
})
export class OrdiniService {

  private apiUrl = FilmService.apiUrl + "/ordini";

  private selectedOrdine : Ordine | null = null;

  private selectedDettaglioOrdine : DettaglioOrdine | null = null;

  constructor(private http: HttpClient) {}

  getOrdini(idCliente:number, pageNumber: number = 0, order: string = ''): Observable<Ordine[]>{
    return this.http.get<Ordine[]>(
      `${this.apiUrl}`, {
        params: {
          idCliente: idCliente,
          p: pageNumber,
          order: order
        }
      });
  }

}
