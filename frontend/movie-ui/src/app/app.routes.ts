import { Routes } from '@angular/router';
import { LoginComponent } from './login.component';
import { MovieTableComponent } from './movie-table.component';

export const routes: Routes = [
  { path: '', redirectTo: 'login', pathMatch: 'full' },
  { path: 'login', component: LoginComponent },
  { path: 'movies', component: MovieTableComponent }
];