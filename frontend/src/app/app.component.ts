import { Component } from '@angular/core';
import { RouterModule, RouterOutlet } from '@angular/router';
import { NavigationBarComponent } from "./navigation-bar/navigation-bar.component";

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, RouterModule, NavigationBarComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'frontend';
}
