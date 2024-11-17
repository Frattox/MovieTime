import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Cliente, ProfileService } from '../services/profile.service';
import { DatePipe } from '@angular/common';

@Component({
  selector: 'app-profile',
  standalone: true,
  imports: [DatePipe],
  templateUrl: './profile.component.html',
  styleUrl: './profile.component.css'
})
export class ProfileComponent implements OnInit{

  private idCliente!: number; 
  cliente!: Cliente;

  constructor(
    private router: ActivatedRoute,
    private route: Router,
    private profileService: ProfileService
  ){}

  ngOnInit(): void {
      this.router.params.subscribe(params=>{
        this.idCliente = +params['idCliente'];
      });
      this.loadProfile();
  }

  loadProfile(){
    this.profileService.getProfile(this.idCliente).subscribe(cliente => {
      this.cliente = cliente;
    });
  }

  logout():void{
    this.route.navigate([``]);
  }

}
