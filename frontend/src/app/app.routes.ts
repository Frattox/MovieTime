import { Routes, RouterModule } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { InfoFilmComponent } from './home/carosello/info-film/info-film.component';

export const routes: Routes = [
    {path:"",component:HomeComponent},
    {path:"film/:id",component:InfoFilmComponent}
];

export const AppRoutingModule = RouterModule.forRoot(routes);