import { Component } from '@angular/core';
import { MovieTableComponent, Movie } from './movie-table.component';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [MovieTableComponent],
  template: `
    <h1>Movie List</h1>
    <app-movie-table [movies]="movies"></app-movie-table>
  `,
})
export class App {
  movies: Movie[] = [
    { title: 'Inception', year: 2010, genres: ['Action', 'Sci-Fi'] },
    { title: 'The Matrix', year: 1999, genres: ['Action', 'Sci-Fi'] },
    { title: 'Interstellar', year: 2014, genres: ['Adventure', 'Drama', 'Sci-Fi'] },
  ];
}