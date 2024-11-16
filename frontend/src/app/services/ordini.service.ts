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

  constructor(private http: HttpClient) {}

  getOrdini(idC:number, p: number = 0, o: string = ''): Observable<Ordine[]>{
    return this.http.get<Ordine[]>(
      `${this.apiUrl}`, {
        params: {
          idCliente: idC,
          pageNumber: p,
          order: o
        }
      });
  }

  getDettagliOrdine(idO: number, idC: number, p: number = 0): Observable<DettaglioOrdine[]> {
    return this.http.get<DettaglioOrdine[]>(
      `${this.apiUrl}/dettagli-ordine`, {
        params:{
          idOrdine: idO,
          idCliente: idC,
          pageNumber: p
        }
      }
    );
  }

  setSelectedOrdine(ordine: Ordine){
    this.selectedOrdine = ordine;
  }

  getSelectedDettaglio(): Ordine | null {
    return this.selectedOrdine;
  }
}
