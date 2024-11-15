import { Component, OnInit } from '@angular/core';
import {MatListModule} from '@angular/material/list';
import { CarrelloService, DettaglioCarrello } from '../services/carrello.service';
import { ActivatedRoute, RouterModule , Router} from '@angular/router';
import { CommonModule } from '@angular/common';
import { Film, FilmService } from '../services/film.service';
import {MatIconModule} from '@angular/material/icon';

@Component({
  selector: 'app-carrello',
  standalone: true,
  imports: [
    MatListModule,
    CommonModule,
    RouterModule,
    MatIconModule
  ],
  templateUrl: './carrello.component.html',
  styleUrl: './carrello.component.css'
})
export class CarrelloComponent implements OnInit{

  idCliente!: number;

  dettagliCarrello : DettaglioCarrello[] | null = [];

  dettagliFilm: { [id: number]: Film } = {};

  constructor(
    private router: Router,
    private carrelloService: CarrelloService,
    private filmService: FilmService,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.idCliente = +params['idCliente'];
    });
    this.loadDettagliCarrello();
  }

  loadDettagliCarrello(): void {
    this.carrelloService.getDettagliCarrello(this.idCliente, 0, 'titolo', 'asc')
      .subscribe(dettagli => {
        if(dettagli!=null)
          dettagli.forEach(dettaglio => {
            this.filmService.getFilmById(dettaglio.filmId).subscribe(film => {
              this.dettagliFilm[dettaglio.filmId] = {
                ...film,
                immagine: `/assets/img/${film.immagine}`
              };
            });
          });
          this.dettagliCarrello = dettagli;
      });
  }
}
