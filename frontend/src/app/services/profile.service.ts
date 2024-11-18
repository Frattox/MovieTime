import { Injectable } from '@angular/core';
import { FilmService } from './film.service';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { OAuthService } from 'angular-oauth2-oidc';

export interface Cliente {
  idCliente: number;
  nome: string;
  cognome: string;
  email: string;
  dataRegistrazione: Date;
}

@Injectable({
  providedIn: 'root'
})
export class ProfileService {

  private apiUrl = FilmService.apiUrl + "/cliente";

  constructor(
    private http: HttpClient,
    private oauthService: OAuthService
  ) {}

  getProfile(): Observable<Cliente>{
    return this.http.get<Cliente>(
      `${this.apiUrl}`, {
        headers: {
          'Authorization':`Bearer ${this.oauthService.getAccessToken()}`
        }
      }
    );
  }

  addCliente(): Observable<String>{
    const ret: Observable<String> = this.http.post<String>(
      this.apiUrl,
      null,
      {
        headers: {
          'Authorization':`Bearer ${this.oauthService.getAccessToken()}`
        }
      }
    );
    ret.subscribe(message => {
      console.log(message);
    });
    return ret;
  }
}
