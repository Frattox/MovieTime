import { Component } from '@angular/core';
import {MatToolbarModule} from '@angular/material/toolbar';
import {MatIconModule} from '@angular/material/icon';
import { Router } from '@angular/router';
import { RouterModule } from '@angular/router';


@Component({
  selector: 'app-navigation-bar',
  standalone: true,
  imports: [MatToolbarModule,MatIconModule,RouterModule],
  templateUrl: './navigation-bar.component.html',
  styleUrl: './navigation-bar.component.css'
})
export class NavigationBarComponent {

  private idCliente = 1;

  constructor(private router: Router) {}

  openCarrello(): void {
    this.router.navigate([`/carrello`, this.idCliente]);
  }

  getIdCliente(): number{
    return this.idCliente;
  }

  goHome(): void{
    this.router.navigate([``]);
  }

}
