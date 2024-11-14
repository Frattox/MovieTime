// carosello.component.ts
import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CarouselModule } from 'primeng/carousel';
import { Router, RouterModule } from '@angular/router';
import { Film } from '../../services/film.service';
import { FilmService } from '../../services/film.service';

@Component({
  selector: 'app-carosello',
  standalone: true,
  imports: [
    CommonModule,
    CarouselModule,
    RouterModule
  ],
  templateUrl: './carosello.component.html',
  styleUrls: ['./carosello.component.css']
})
export class CaroselloComponent implements OnInit{

  films: Film[] = [];
  
  constructor(private filmService: FilmService, private router: Router) {}

  ngOnInit(): void {
    this.filmService.getFilms().subscribe((films) => {
      this.films = films.map(film => ({
        ...film,
        immagine: `assets/img/${film.immagine}`
      }));
    });
  }

  selectFilm(film: Film): void {
    this.filmService.setSelectedFilm(film);
    console.log(film);
    this.router.navigate(['film', film.idFilm]);
  }  
}
