import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FilmService } from '../../../services/film.service';
import { Film } from '../../../services/film.service';

@Component({
  selector: 'app-info-film',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './info-film.component.html',
  styleUrl: './info-film.component.css'
})
export class InfoFilmComponent implements OnInit{
  film: Film | null | undefined;

  constructor(private filmService: FilmService) {}

  ngOnInit(): void {
    this.film = this.filmService.getSelectedFilm();
  }
}
