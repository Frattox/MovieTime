import { Component, OnInit } from '@angular/core';
import { DettaglioOrdine, OrdiniService } from '../../services/ordini.service';
import { ActivatedRoute } from '@angular/router';
import { Film, FilmService } from '../../services/film.service';
import {MatListModule} from '@angular/material/list';

@Component({
  selector: 'app-info-ordine',
  standalone: true,
  imports: [MatListModule],
  templateUrl: './info-ordine.component.html',
  styleUrl: './info-ordine.component.css'
})
export class InfoOrdineComponent implements OnInit{
  idCliente!: number;
  idOrdine!: number;
  dettagliOrdine!: DettaglioOrdine[];
  dettagliFilm: { [id: number]: Film } = {};
  constructor(
    private ordiniService: OrdiniService,
    private filmService: FilmService,
    private route: ActivatedRoute
  ){}

  ngOnInit(): void{
    this.route.params.subscribe(params => {
      this.idCliente = +params['idCliente'];
      this.idOrdine = +params['idOrdine']
    });
    this.loadDettagliOrdine();
  }
  loadDettagliOrdine():void{
    console.log(this.idOrdine);
    this.ordiniService.getDettagliOrdine(this.idOrdine,this.idCliente).subscribe(dettagli =>{
      if(dettagli!=null)
        dettagli.forEach(dettaglio => {
          this.filmService.getFilmById(dettaglio.filmId).subscribe(film => {
            this.dettagliFilm[dettaglio.filmId] = {
              ...film,
              immagine: `/assets/img/${film.immagine}`
            };
          });
        });
        this.dettagliOrdine = dettagli;
    });
  }
}
