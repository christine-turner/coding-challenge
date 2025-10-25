import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { BehaviorSubject, Observable, tap } from 'rxjs';
import { Movie } from '../components/movie-table.component';

@Injectable({ providedIn: 'root' })
export class DataService {
  constructor(private http: HttpClient) {}

  private filtersSource = new BehaviorSubject<{
    title: string;
    year: string;
    genre: string;
  } | null>(null);
  filters$ = this.filtersSource.asObservable();
  setFilters(filterValues: { title: string; year: string; genre: string }) {
    this.filtersSource.next(filterValues);
  }

  private moviesSource = new BehaviorSubject<Movie[]>([]);
  movies$ = this.moviesSource.asObservable();

  private getMovies(
    username: string,
    password: string,
    filters?: { title?: string; year?: string; genre?: string }
  ): Observable<Movie[]> {
    const authHeader = 'Basic ' + btoa(`${username}:${password}`);
    let params = new HttpParams();

    if (filters) {
      if (filters.title) params = params.set('title', filters.title);
      if (filters.year) params = params.set('year', filters.year);
      if (filters.genre) params = params.set('genre', filters.genre);
    }
    const headers = new HttpHeaders({ Authorization: authHeader });
    return this.http.get<Movie[]>('http://localhost:8080/api/movies', { headers, params }).pipe(
      tap((movies) => this.moviesSource.next(movies)) // update the shared data
    );
  }

  public loadMovies(
    username: string,
    password: string,
    filters?: { title?: string; year?: string; genres?: string }
  ): void {
    // note: my understanding is HTTP observables in Angular automatically complete
    // after emitting one value so haven't unsubscribed here
    this.getMovies(username, password, filters).subscribe({
      next: (movies) => this.moviesSource.next(movies),
      error: (err) => {
        alert('Credentials invalid - error loading movies');
        this.moviesSource.next([]);
      },
    });
  }
}
