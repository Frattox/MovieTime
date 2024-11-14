import { Routes, RouterModule } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { InfoFilmComponent } from './home/carosello/info-film/info-film.component';
import { CarrelloComponent } from './carrello/carrello.component';

export const routes: Routes = [
    {path:"",component:HomeComponent},
    {path:"film/:id",component:InfoFilmComponent},
    {path:"ordini/:id",component:InfoFilmComponent},
    {path:"carrello/:id",component:CarrelloComponent},
    {path:"profile/:id",component:InfoFilmComponent}

];

export const AppRoutingModule = RouterModule.forRoot(routes);