import { Component, OnInit } from '@angular/core';
import { InputTextModule } from 'primeng/inputtext';
import { FormsModule } from '@angular/forms';
import { FloatLabelModule } from 'primeng/floatlabel';
import { ButtonModule } from 'primeng/button';
import { ActivatedRoute, Router } from '@angular/router';
import { MetodiDiPagamentoService, MetodoDiPagamento } from '../services/metodi-di-pagamento.service';
import { CommonModule } from '@angular/common';
import { RadioButtonModule } from 'primeng/radiobutton';

@Component({
  selector: 'app-pagamento',
  standalone: true,
  imports: [
    InputTextModule,
    FormsModule,
    FloatLabelModule,
    ButtonModule,
    CommonModule,
    RadioButtonModule
  ],
  templateUrl: './pagamento.component.html',
  styleUrls: ['./pagamento.component.css']
})
export class PagamentoComponent implements OnInit {
  tipo: string = '';
  numero!: number;
  idCliente!: number;
  metodiDiPagamento!: MetodoDiPagamento[];
  metodoSelezionato?: MetodoDiPagamento;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private metodoDiPagamentoService: MetodiDiPagamentoService
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
          console.log(metodiDiPagamento);
        }
      });
  }

  ordina(): void {
    if (this.metodoSelezionato) {
      console.log("Metodo di pagamento selezionato: ", this.metodoSelezionato);
    } else {
      console.log("Inserimento nuovo metodo di pagamento: ", this.tipo, this.numero);
    }
  }

  selezionaMetodo(metodo: MetodoDiPagamento): void {
    this.metodoSelezionato = metodo;
  }
}
