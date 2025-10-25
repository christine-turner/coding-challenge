import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Movie } from './movie-table.component';

@Injectable({ providedIn: 'root' })
export class AuthService {
  private authHeader: string | null = null;
  private loggedIn = false;

  constructor(private http: HttpClient) {}

  // Store credentials locally and return success
  login(username: string, password: string): boolean {
    if (username && password) {
      this.authHeader = 'Basic ' + btoa(`${username}:${password}`);
      this.loggedIn = true;
      return true;
    }
    return false;
  }

  isLoggedIn(): boolean {
    return this.loggedIn;
  }

  getMovies(): Observable<Movie[]> {
    if (!this.loggedIn || !this.authHeader) {
      throw new Error('User is not logged in');
    }

    const headers = new HttpHeaders({ Authorization: this.authHeader });
    return this.http.get<Movie[]>('http://localhost:8080/api/movies', { headers });
  }
}
