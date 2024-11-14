import { Component, OnInit } from '@angular/core';
import {MatListModule} from '@angular/material/list';
import { CarrelloService, DettaglioCarrello } from '../services/carrello.service';
import { ActivatedRoute } from '@angular/router';
import { CommonModule } from '@angular/common';
import { Film, FilmService } from '../services/film.service';

@Component({
  selector: 'app-carrello',
  standalone: true,
  imports: [MatListModule,CommonModule],
  templateUrl: './carrello.component.html',
  styleUrl: './carrello.component.css'
})
export class CarrelloComponent implements OnInit{

  idCliente!: number;

  dettagliCarrello : DettaglioCarrello[] | null = [];

  dettagliFilm: { [id: number]: Film } = {};

  constructor(
    private carrelloService: CarrelloService,
    private filmService: FilmService,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.idCliente = +params['id'];
    });
    this.loadDettagliCarrello();
  }

  loadDettagliCarrello(): void {
    this.carrelloService.getDettagliCarrello(this.idCliente, 0, 'titolo', 'asc')
      .subscribe(dettagli => {
        this.dettagliCarrello = dettagli;
        if(this.dettagliCarrello!=null)
          this.dettagliCarrello.forEach(dettaglio => {
            this.filmService.getFilmById(dettaglio.filmId).subscribe(film => {
              this.dettagliFilm[dettaglio.filmId] = {
                ...film,
                immagine: `assets/img/${film.immagine}`
              };
            });
          });
      });
  }
}
