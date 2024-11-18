import { Component } from '@angular/core';
import {MatToolbarModule} from '@angular/material/toolbar';
import {MatIconModule} from '@angular/material/icon';
import { Router } from '@angular/router';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Film } from '../services/film.service';

@Component({
  selector: 'app-navigation-bar',
  standalone: true,
  imports: [
    MatToolbarModule,
    MatIconModule,
    RouterModule,
    CommonModule,
    FormsModule
  ],
  templateUrl: './navigation-bar.component.html',
  styleUrl: './navigation-bar.component.css'
})
export class NavigationBarComponent {

  searchActive: boolean = false;
  searchTerm: string = '';
  films: Film[] = [];

  constructor(
    private router: Router
  ) {}

  openCarrello(): void {
    this.searchActive = false;
    this.router.navigate([`/carrello`]);
  }

  goHome(): void{
    this.searchActive = false;
    this.router.navigate([``]);
  }

  deactiveSearch(){
    this.searchActive = false;
  }

  toggleSearch(): void {
    this.searchActive = !this.searchActive;
    if (!this.searchActive) {
      this.searchTerm = '';
      this.films = [];
      this.goHome();
    }else{
      this.router.navigate([`/search`]);
    }
  }

  searchFilms(): void {
    if (this.searchTerm.trim() === '') {
      return;
    }
    this.router.navigate(['/search'], {
      queryParams: { term: this.searchTerm },
    });
  }

}
