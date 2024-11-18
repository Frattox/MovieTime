import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Cliente, ProfileService } from '../services/profile.service';
import { DatePipe } from '@angular/common';
import { OAuthService } from 'angular-oauth2-oidc';

@Component({
  selector: 'app-profile',
  standalone: true,
  imports: [DatePipe],
  templateUrl: './profile.component.html',
  styleUrl: './profile.component.css'
})
export class ProfileComponent implements OnInit{

  cliente!: Cliente;

  constructor(
    private router: ActivatedRoute,
    private route: Router,
    private profileService: ProfileService,
    private oauthService: OAuthService
  ){}

  ngOnInit(): void {
      this.router.params.subscribe();
      this.loadProfile();
  }

  loadProfile(){
    this.profileService.getProfile().subscribe(cliente => {
      this.cliente = cliente;
    });
  }

  logout():void{
    this.route.navigate([``]);
    this.oauthService.logOut();
  }

}
