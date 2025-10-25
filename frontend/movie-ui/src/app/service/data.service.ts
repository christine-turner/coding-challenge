import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { BehaviorSubject, Observable, tap } from 'rxjs';
import { Movie } from '../components/movie-table.component';

@Injectable({ providedIn: 'root' })
export class DataService {
  private moviesSource = new BehaviorSubject<Movie[]>([]);
  movies$ = this.moviesSource.asObservable();
  constructor(private http: HttpClient) {}

  private getMovies(username: string, password: string): Observable<Movie[]> {
    const authHeader = 'Basic ' + btoa(`${username}:${password}`);
    const headers = new HttpHeaders({ Authorization: authHeader });
    return this.http.get<Movie[]>('http://localhost:8080/api/movies', { headers }).pipe(
      tap((movies) => this.moviesSource.next(movies)) // update the shared data
    );
  }

  public loadMovies(username: string, password: string): void {
    this.getMovies(username, password).subscribe({
      next: (movies) => this.moviesSource.next(movies),
      error: (err) => {
        alert('Credentials invalid - error loading movies');
        this.moviesSource.next([]);
      },
    });
  }
}
