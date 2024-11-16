import { Component, OnInit } from '@angular/core';
import { InputTextModule } from 'primeng/inputtext';
import { FormsModule } from '@angular/forms';
import { FloatLabelModule } from 'primeng/floatlabel';
import { ButtonModule } from 'primeng/button';
import { ActivatedRoute, Router } from '@angular/router';
import { MetodiDiPagamentoService, MetodoDiPagamento } from '../services/metodi-di-pagamento.service';
import { RadioButtonModule } from 'primeng/radiobutton';
import {MatSnackBar} from '@angular/material/snack-bar';
import { CommonModule } from '@angular/common';
import {MatListModule} from '@angular/material/list';

@Component({
  selector: 'app-pagamento',
  standalone: true,
  imports: [
    InputTextModule,
    FormsModule,
    FloatLabelModule,
    ButtonModule,
    RadioButtonModule,
    CommonModule,
    MatListModule
  ],
  templateUrl: './pagamento.component.html',
  styleUrls: ['./pagamento.component.css']
})
export class PagamentoComponent implements OnInit {
  tipo: string = '';
  numero!: number;
  idCliente!: number;
  metodiDiPagamento!: MetodoDiPagamento[];

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private metodoDiPagamentoService: MetodiDiPagamentoService,
    private snackBar: MatSnackBar
  ) {}

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.idCliente = +params['idCliente'];
    });
    this.loadMetodiDiPagamento();
  }

  loadMetodiDiPagamento(): void {
    this.metodoDiPagamentoService.getMetodiPagamento(this.idCliente)
      .subscribe(metodiDiPagamento => {
        if (metodiDiPagamento != null) {
          this.metodiDiPagamento = metodiDiPagamento;
        }
      });
  }

  ordina(): void {
    if(!this.metodoDiPagamentoService.getSelectedMetodoDiPagamento()){
      var metodo : MetodoDiPagamento = {
        idCliente: this.idCliente,
        numero: this.numero,
        tipo: this.tipo
      }
      this.metodoDiPagamentoService.setSelectedMetodoDiPagamento(metodo);
    }
      this.metodoDiPagamentoService.addMetodoPagamento();
      this.router.navigate([``]);
      this.snackBar.open('Ordine effettuato!', "Ok.");
  }

  selezionaMetodo(metodo: MetodoDiPagamento): void {
    this.metodoDiPagamentoService.setSelectedMetodoDiPagamento(metodo)
  }
}
