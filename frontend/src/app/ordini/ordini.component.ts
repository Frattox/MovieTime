import { Component, OnInit } from '@angular/core';
import { OrdiniService, Ordine, DettaglioOrdine } from '../services/ordini.service';
import { ActivatedRoute, RouterModule, Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FilmService, Film } from '../services/film.service';
import { MatListModule } from '@angular/material/list';
import { MatIconModule } from '@angular/material/icon';

@Component({
  selector: 'app-ordini',
  standalone: true,
  imports: [
    MatListModule,
    CommonModule,
    RouterModule,
    MatIconModule
  ],
  templateUrl: './ordini.component.html',
  styleUrls: ['./ordini.component.css']
})
export class OrdiniComponent implements OnInit {

  idCliente!: number;
  ordini: Ordine[] | null = [];

  constructor(
    private ordiniService: OrdiniService,
    private router: Router,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.idCliente = +params['idCliente'];
    });
    this.loadOrdini();
  }

  loadOrdini(): void {
    this.ordiniService.getOrdini(this.idCliente)
      .subscribe(ordini => {
        if (ordini != null) {
          this.ordini = ordini;
        }
      });
  }

  selectOrdine(ordine: Ordine): void{
    this.ordiniService.setSelectedOrdine(ordine);
    this.router.navigate(['ordini', this.idCliente, ordine.idOrdine]);
  }
}
