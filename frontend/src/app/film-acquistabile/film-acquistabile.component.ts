import { Component, Input, Output, EventEmitter} from '@angular/core';
import { CommonModule } from '@angular/common';
import { CarrelloService } from '../services/carrello.service';
import { Router } from '@angular/router';

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

  constructor(private router: Router){}

  onSelect() {
    this.filmSelected.emit(this.film);
  }
  
}
