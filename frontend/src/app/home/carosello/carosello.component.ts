// carosello.component.ts
import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CarouselModule } from 'primeng/carousel';
import { Router, RouterModule } from '@angular/router';
import { FilmService } from './film.service';

export interface Film {
  id: number;
  titolo: string;
  regista: string;
  prezzo: number;
  immagine: string;
}

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
export class CaroselloComponent {
  films: Film[] = [
    { id: 1, titolo: 'Pulp Fiction', regista: 'Quentin Tarantino', prezzo: 20, immagine: 'assets/img/pulp_fiction.jpg' },
    { id: 2, titolo: 'Inception', regista: 'Christopher Nolan', prezzo: 18, immagine: 'assets/img/inception.jpg' },
    { id: 3, titolo: 'Interstellar', regista: 'Christopher Nolan', prezzo: 25, immagine: 'assets/img/interstellar.jpg' },
    { id: 4, titolo: 'Tenet', regista: 'Christopher Nolan', prezzo: 22, immagine: 'assets/img/tenet.jpg' },
    { id: 5, titolo: 'The Dark Knight', regista: 'Christopher Nolan', prezzo: 24, immagine: 'assets/img/the_dark_knight.jpg' },
    { id: 6, titolo: 'Fight Club', regista: 'David Fincher', prezzo: 19, immagine: 'assets/img/fight_club.jpg' },
    { id: 7, titolo: 'The Matrix', regista: 'Lana e Lilly Wachowski', prezzo: 21, immagine: 'assets/img/the_matrix.jpg' },
    { id: 8, titolo: 'The Shawshank Redemption', regista: 'Frank Darabont', prezzo: 20, immagine: 'assets/img/the_shawshank_redemption.jpg' },
    { id: 9, titolo: 'Forrest Gump', regista: 'Robert Zemeckis', prezzo: 17, immagine: 'assets/img/forrest_gump.jpg' },
    { id: 10, titolo: 'The Godfather', regista: 'Francis Ford Coppola', prezzo: 23, immagine: 'assets/img/the_godfather.jpg' },
    { id: 11, titolo: 'The Lord of the Rings: The Fellowship of the Ring', regista: 'Peter Jackson', prezzo: 22, immagine: 'assets/img/the_lord_of_the_rings.jpg' },
    { id: 12, titolo: 'Gladiator', regista: 'Ridley Scott', prezzo: 18, immagine: 'assets/img/gladiator.jpg' }
  ];

  constructor(private filmService: FilmService, private router: Router) {}

  selectFilm(film: Film): void {
    this.filmService.setSelectedFilm(film);
    this.router.navigate(['/film', film.id]);
  }
}
