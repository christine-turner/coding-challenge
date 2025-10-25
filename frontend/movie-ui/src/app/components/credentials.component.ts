import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../service/auth.service';
import { DataService } from '../service/data.service';
import { BehaviorSubject } from 'rxjs/internal/BehaviorSubject';
import { Subscription } from 'rxjs';

@Component({
  selector: 'credentials-component',
  standalone: true,
  imports: [FormsModule],
  template: `
    <div class="login-container">
      <h2>Movie Database</h2>
      Please enter your credentials so you can access the movie database.

      <input [(ngModel)]="username" (ngModelChange)="updateCredentials()" placeholder="Username" />

      <input
        [(ngModel)]="password"
        type="password"
        (ngModelChange)="updateCredentials()"
        placeholder="Password"
      />

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
  filters?: { title?: string; year?: string; genres?: string };
  private filtersSubscription!: Subscription;
  username = '';
  password = '';

  constructor(private authService: AuthService, private dataService: DataService) {}
  ngOnInit() {
    this.filtersSubscription = this.dataService.filters$.subscribe((filters) => {
      this.filters = filters || undefined;
    });
  }
  // Called on every keystroke in input fields
  updateCredentials() {
    this.authService.setCredentials(this.username, this.password);
  }

  search() {
    this.updateCredentials();
    this.dataService.loadMovies(this.username, this.password, this.filters);
  }

  ngOnDestroy() {
    this.filtersSubscription.unsubscribe(); // prevents memory leak
  }
}
