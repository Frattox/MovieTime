import { Component, inject, Input, OnInit } from '@angular/core';
import {MatListModule} from '@angular/material/list';
import { CarrelloService, DettaglioCarrello } from '../services/carrello.service';
import { ActivatedRoute } from '@angular/router';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-carrello',
  standalone: true,
  imports: [MatListModule,CommonModule],
  templateUrl: './carrello.component.html',
  styleUrl: './carrello.component.css'
})
export class CarrelloComponent implements OnInit{

  idCliente!: number;

  private dettagliCarrello : DettaglioCarrello[] | null = [];

  constructor(
    private carrelloService: CarrelloService,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.idCliente = +params['id'];
      this.loadDettagliCarrello();
    });
  }

  loadDettagliCarrello(): void {
    this.carrelloService.getDettagliCarrello(this.idCliente, 0, 'titolo', 'asc')
      .subscribe(dettagli => {
        this.dettagliCarrello = dettagli;
      });
  }

  getDettagliCarrello(): DettaglioCarrello[] | null{
    console.log(this.dettagliCarrello)
    return this.dettagliCarrello;
  }

}
