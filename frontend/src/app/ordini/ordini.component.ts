import { Component, OnInit } from '@angular/core';
import { OrdiniService, Ordine, DettaglioOrdine } from '../services/ordini.service';
import { ActivatedRoute, RouterModule, Router } from '@angular/router';
import { CommonModule } from '@angular/common';
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
  ordini: Ordine[] | null = [];

  constructor(
    private ordiniService: OrdiniService,
    private router: Router,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.loadOrdini();
  }

  loadOrdini(): void {
    this.ordiniService.getOrdini()
      .subscribe(ordini => {
        if (ordini != null) {
          this.ordini = ordini;
        }
      });
  }

  selectOrdine(ordine: Ordine): void{
    this.ordiniService.setSelectedOrdine(ordine);
    this.router.navigate(['ordini', ordine.idOrdine]);
  }
}
