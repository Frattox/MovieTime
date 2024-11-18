import { APP_INITIALIZER, ApplicationConfig, importProvidersFrom, inject } from '@angular/core';
import { provideZoneChangeDetection } from '@angular/core';
import { provideRouter } from '@angular/router';
import { isPlatformBrowser } from '@angular/common';
import { PLATFORM_ID } from '@angular/core';

import { routes } from './app.routes';
import { provideClientHydration } from '@angular/platform-browser';
import { provideAnimationsAsync } from '@angular/platform-browser/animations/async';
import { provideHttpClient, withFetch } from '@angular/common/http';
import { AuthConfig, OAuthService, OAuthModule } from 'angular-oauth2-oidc';

// Configurazione OAuth senza accesso diretto a window
export const authCodeFlowConfig: AuthConfig = {
  issuer: 'http://localhost:8080/realms/movietime',
  tokenEndpoint: 'http://localhost:8080/realms/movietime/protocol/openid-connect/token',
  clientId: 'fra',
  responseType: 'code',
  scope: 'openid profile'
};

function initializeOAuth(oauthService: OAuthService): () => Promise<any> {
  const platformId = inject(PLATFORM_ID);
  return () => {
    if (isPlatformBrowser(platformId)) {
      authCodeFlowConfig.redirectUri = window.location.origin;
      oauthService.configure(authCodeFlowConfig);
      oauthService.setupAutomaticSilentRefresh();
      return oauthService.loadDiscoveryDocumentAndLogin();
    } else {
      return Promise.resolve();
    }
  };
}

export const appConfig: ApplicationConfig = {
  providers: [
    provideZoneChangeDetection({ eventCoalescing: true }),
    provideRouter(routes),
    provideClientHydration(),
    provideAnimationsAsync(),
    provideHttpClient(withFetch()),
    importProvidersFrom(OAuthModule.forRoot()),
    {
      provide: APP_INITIALIZER,
      useFactory: initializeOAuth,
      multi: true,
      deps: [OAuthService]
    }
  ]
};