// film.service.ts
import { Injectable } from '@angular/core';
import { Film } from './home/carosello/carosello.component';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class FilmService {

  private apiUrl = 'http://localhost:8080';

  private selectedFilm: Film | null = null;

  constructor(private http: HttpClient) {}

  getFilms(): Observable<Film[]> {
    return this.http.get<Film[]>(`${this.apiUrl}/film`);
  }

  setSelectedFilm(film: Film): void {
    this.selectedFilm = film;
  }

  getSelectedFilm(): Film | null {
    return this.selectedFilm;
  }
}
