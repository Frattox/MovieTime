// film.service.ts
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Film {
  idFilm: number;
  regista: Regista;
  titolo: string;
  annoUscita: number;
  genere: string;
  formato: string;
  prezzo: number;
  quantita: number;
  immagine: string;
}

export interface Regista{
  idRegista: string;
  nome: string;
  cognome: string;
  dataN: Date;
  nazionalita: string;
}

@Injectable({
  providedIn: 'root'
})
export class FilmService {

  public static apiUrl = 'http://localhost:8080';

  private baseUrl = FilmService.apiUrl + '/film';

  private selectedFilm: Film | null = null;

  constructor(private http: HttpClient) {}

  getFilms(): Observable<Film[]> {
    return this.http.get<Film[]>(`${FilmService.apiUrl}/film`);
  }

  getFilmById(idFilm: number): Observable<Film> {
    return this.http.get<Film>(`${FilmService.apiUrl}/film/${idFilm}`);
  }


  setSelectedFilm(film: Film): void {
    this.selectedFilm = film;
  }

  getSelectedFilm(): Film | null {
    return this.selectedFilm;
  }

  searchFilms(title: string, p: number = 0): Observable<Film[]>{
    return this.http.get<any[]>(`${this.baseUrl}/search`, {
      params: { title, page: p },
    });
  }
}
