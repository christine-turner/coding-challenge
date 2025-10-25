import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CredentialsComponent } from './credentials.component';
import { MovieTableComponent } from './movie-table.component';

@Component({
  selector: 'movie-outer-component',
  standalone: true,
  imports: [CommonModule, MovieTableComponent, CredentialsComponent],
  template: `<credentials-component></credentials-component> <app-movie-table></app-movie-table>`,
})
export class MovieOuterComponent {}
