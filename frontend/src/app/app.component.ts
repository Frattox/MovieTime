import { Component } from '@angular/core';
import { RouterModule, RouterOutlet } from '@angular/router';
import { NavigationBarComponent } from "./navigation-bar/navigation-bar.component";
import { OAuthService } from 'angular-oauth2-oidc';
import { ProfileService } from './services/profile.service';
import { filter } from 'rxjs';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, RouterModule, NavigationBarComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'frontend';

  constructor(
    private oauthService: OAuthService,
    private profileService: ProfileService
  ){}

  ngOnInit(): void {
    this.oauthService.events
      .pipe(filter(e => e.type === 'token_received'))
      .subscribe(() => {
        console.log('Token ricevuto, facendo partire la chiamata...');
        this.profileService.addCliente();
      });

    if (this.oauthService.hasValidAccessToken()) {
      console.log('Token già presente, facendo partire la chiamata...');
      this.profileService.addCliente();
    }
  }
}
