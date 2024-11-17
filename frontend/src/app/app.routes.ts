import { Routes, RouterModule } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { InfoFilmComponent } from './home/carosello/info-film/info-film.component';
import { CarrelloComponent } from './carrello/carrello.component';
import { OrdiniComponent } from './ordini/ordini.component';
import { InfoOrdineComponent } from './ordini/info-ordine/info-ordine.component';
import { PagamentoComponent } from './pagamento/pagamento.component';
import { ProfileComponent } from './profile/profile.component';
import { SearchComponent } from './search/search.component';

export const routes: Routes = [
    {path:"",component:HomeComponent},
    {path:"film/:idCliente",component:InfoFilmComponent},
    {path:"carrello/:idCliente",component:CarrelloComponent},
    {path:"ordini/:idCliente",component:OrdiniComponent},
    {path:"ordini/:idCliente/:idOrdine",component:InfoOrdineComponent},
    {path:"profile/:idCliente",component:ProfileComponent},
    {path:"pagamento/:idCliente",component:PagamentoComponent},
    {path:"search",component:SearchComponent}

];

export const AppRoutingModule = RouterModule.forRoot(routes);