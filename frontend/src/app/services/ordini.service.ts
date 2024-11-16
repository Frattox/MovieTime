import { Injectable } from '@angular/core';
import { FilmService } from './film.service';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { DettaglioCarrello } from './carrello.service';

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

export interface CarrelloDTO {
  idDettagliCarrello: number[];  
  quantita: number[];            
  prezzi: number[];              
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

  acquistaDalCarrello(
    i: string,
    idC: number,
    n: number,
    dettagliCarrello: DettaglioCarrello[]
  ): Observable<String>{

    const carrelloDTO: CarrelloDTO = {
      idDettagliCarrello: dettagliCarrello.map(dettaglio => dettaglio.idDettaglioCarrello),
      quantita: dettagliCarrello.map(dettaglio => dettaglio.quantita),
      prezzi: dettagliCarrello.map(dettaglio => dettaglio.prezzoUnita)
    };
    var ret = this.http.post<string>(
      `${this.apiUrl}/acquistaDalCarrello`, 
      carrelloDTO,
      {
        params: {
          idCliente: idC,
          indirizzo: i,
          numero: n
        }
      }
    )
    ret.subscribe();
    return ret;
  }
}
