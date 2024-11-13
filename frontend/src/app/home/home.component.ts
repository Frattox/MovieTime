import { Component } from '@angular/core';
import { NavigationBarComponent } from "../navigation-bar/navigation-bar.component";
import { CaroselloComponent } from "./carosello/carosello.component";

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [NavigationBarComponent, CaroselloComponent],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent {

}
