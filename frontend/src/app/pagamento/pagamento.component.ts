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
import { MatListModule } from '@angular/material/list';
import { CarrelloService } from '../services/carrello.service';

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
  tipo: string  | null = '';
  indirizzo: string | null = null;
  numero: number | null = null;
  metodiDiPagamento!: MetodoDiPagamento[];
  metodoSelezionato?: MetodoDiPagamento;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private metodoDiPagamentoService: MetodiDiPagamentoService,
    private carrelloService: CarrelloService,
    private snackBar: MatSnackBar
  ) {}

  ngOnInit(): void {
    this.route.params.subscribe();
    this.loadMetodiDiPagamento();
  }

  loadMetodiDiPagamento(): void {
    this.metodoDiPagamentoService.getMetodiPagamento()
      .subscribe(metodiDiPagamento => {
        if (metodiDiPagamento != null) {
          this.metodiDiPagamento = metodiDiPagamento;
        }
      });
  }

  ordina(): void {
    if (!this.indirizzo) {
      this.snackBar.open('Inserisci un indirizzo!', 'Ok');
      return;
    }
  
    const metodoSelezionato = this.metodoDiPagamentoService.getSelectedMetodoDiPagamento();
  
    if (!metodoSelezionato) {
      if (!this.numero || !this.tipo) {
        this.snackBar.open('Inserisci un metodo di pagamento valido!', 'Ok');
        return;
      }
  
      const metodo: MetodoDiPagamento = {
        numero: this.numero,
        tipo: this.tipo,
      };
  
      this.metodoDiPagamentoService.setSelectedMetodoDiPagamento(metodo);
    }
  
    const metodoPagamentoFinale = this.metodoDiPagamentoService.getSelectedMetodoDiPagamento();
    
    if (!metodoPagamentoFinale || !metodoPagamentoFinale.numero) {
      this.snackBar.open('Errore durante la selezione del metodo di pagamento!', 'Ok');
      return;
    }
  
    this.metodoDiPagamentoService.addMetodoPagamento();
    this.carrelloService.acquistaDalCarrello(this.indirizzo, metodoPagamentoFinale.numero);
  
    this.router.navigate([``]);
    this.snackBar.open('Ordine effettuato!', 'Ok');
  }
  

  selezionaMetodo(metodo: MetodoDiPagamento): void {
    this.metodoSelezionato = metodo;
    this.metodoDiPagamentoService.setSelectedMetodoDiPagamento(metodo)
  }
}
