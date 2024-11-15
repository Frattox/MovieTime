import { Component, Input, Output, EventEmitter} from '@angular/core';
import { CommonModule } from '@angular/common';
import { CarrelloService } from '../../services/carrello.service';

@Component({
  selector: 'app-film-acquistabile',
  standalone: true,
  imports: [
    CommonModule
  ],
  templateUrl: './film-acquistabile.component.html',
  styleUrl: './film-acquistabile.component.css'
})
export class FilmAcquistabileComponent {
  @Input() film: any;
  @Output() filmSelected = new EventEmitter<any>();

  constructor(private carrelloService: CarrelloService) {}


  onSelect() {
    this.filmSelected.emit(this.film);
  }

  aggiungiAlCarrello(): void {
    const idCliente = 1;
    const idFilm = this.film.idFilm;
    const quantity = 1;
  
    this.carrelloService.aggiungiAlCarrello(idCliente, idFilm, quantity).subscribe({
      next: (response) => {
        this.showPopup(`Film "${this.film.titolo}" aggiunto al carrello!`);
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
