import { Component } from '@angular/core';
import { MovieTableComponent, Movie } from './movie-table.component';
import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, MovieTableComponent], 
  templateUrl: './app.component.html'
})
export class App {
 /* movies: Movie[] = [
    { title: 'Inception', year: 2010, genres: ['Action', 'Sci-Fi'] },
    { title: 'The Matrix', year: 1999, genres: ['Action', 'Sci-Fi'] },
    { title: 'Interstellar', year: 2014, genres: ['Adventure', 'Drama'] }
  ]; */

    movies: Movie[] = [];

  constructor(private http: HttpClient) {}

  ngOnInit() {
    this.http.get<Movie[]>('http://localhost:8080/api/movies')
      .subscribe(data => {
        this.movies = data;
      });
  }
}