import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FilmService } from '../../../services/film.service';
import { Film } from '../../../services/film.service';
import {MatIconModule} from '@angular/material/icon';
import { CarrelloService } from '../../../services/carrello.service';
import { InputNumberModule } from 'primeng/inputnumber';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-info-film',
  standalone: true,
  imports: [
    CommonModule,
    MatIconModule,
    InputNumberModule,
    FormsModule
  ],
  templateUrl: './info-film.component.html',
  styleUrl: './info-film.component.css'
})
export class InfoFilmComponent implements OnInit{

  quantity: number = 0;

  film: Film | null = null;

  constructor(
    private filmService: FilmService,
    private carrelloService: CarrelloService
  ) {}

  ngOnInit(): void {
    this.film = this.filmService.getSelectedFilm();
  }

  aggiungiAlCarrello(): void {
    if(this.quantity<=0)
      return;
    const idCliente = 1;
    const idFilm = this.film?.idFilm;
  
    this.carrelloService.aggiungiAlCarrello(idCliente, idFilm, this.quantity).subscribe({
      next: (response) => {
        this.showPopup(`Film "${this.film?.titolo}" aggiunto al carrello!`);
      },
      error: (err) => {
        console.error('Errore durante l\'aggiunta al carrello:', err);
        this.showPopup('Errore durante l\'aggiunta al carrello.');
      }
    });
  }

  showPopup(message: string): void {
    alert(message);
  }
}
