import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Film, FilmService } from '../services/film.service';
import { FilmAcquistabileComponent } from '../film-acquistabile/film-acquistabile.component';
@Component({
  selector: 'app-search',
  standalone: true,
  imports: [
    FilmAcquistabileComponent
  ],
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css'],
})
export class SearchComponent implements OnInit {
  searchTerm: string = '';
  films: Film[] = [];

  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private filmService: FilmService
  ) {}

  ngOnInit(): void {
    this.route.queryParams.subscribe((params) => {
      this.searchTerm = params['term'] || '';
      if (this.searchTerm) {
        this.performSearch();
      }
    });
  }

  performSearch(): void {
    this.filmService.searchFilms(this.searchTerm).subscribe((films) => {
      this.films = films.map(film => ({
        ...film,
        immagine: `assets/img/${film.immagine}`
      }));
    });
  }

  selectFilm(film: Film): void{
    this.filmService.setSelectedFilm(film);
    this.router.navigate(['film', film.idFilm]);
  }
}
