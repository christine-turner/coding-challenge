import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { DataService } from '../service/data.service';

@Component({
  selector: 'credentials-component',
  standalone: true,
  imports: [FormsModule],
  template: `
    <div class="login-container">
      <h2>Movie Database</h2>
      Please enter your credentials so you can access the movie database.
      <input [(ngModel)]="username" placeholder="Username" />
      <input [(ngModel)]="password" type="password" placeholder="Password" />
      <button (click)="search()">Search</button>
    </div>
  `,
  styles: [
    `
      .login-container {
        display: flex;
        flex-direction: column;
        align-items: center;
        gap: 0.5rem;
        margin-top: 2rem;
      }
    `,
  ],
})
export class CredentialsComponent {
  username = '';
  password = '';

  constructor(private dataService: DataService, private router: Router) {}

  search() {
    this.dataService.loadMovies(this.username, this.password);
  }
}
