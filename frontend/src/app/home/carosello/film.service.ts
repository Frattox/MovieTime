// film.service.ts
import { Injectable } from '@angular/core';
import { Film } from './carosello.component';

@Injectable({
  providedIn: 'root'
})
export class FilmService {
  private selectedFilm: Film | null = null;

  setSelectedFilm(film: Film): void {
    this.selectedFilm = film;
  }

  getSelectedFilm(): Film | null {
    return this.selectedFilm;
  }
}
