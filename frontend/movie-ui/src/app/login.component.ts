import { Component, EventEmitter, Output } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from './auth.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [FormsModule],
  template: `
    <div class="login-container">
      <h2>Login</h2>
      <input [(ngModel)]="username" placeholder="Username" />
      <input [(ngModel)]="password" type="password" placeholder="Password" />
      <button (click)="login()">Login</button>
    </div>
  `,
  styles: [`
    .login-container {
      display: flex;
      flex-direction: column;
      align-items: center;
      gap: 0.5rem;
      margin-top: 2rem;
    }
  `]
})
export class LoginComponent {
  username = '';
  password = '';

  @Output() loginSuccess = new EventEmitter<string>();

  constructor(private auth: AuthService, private router: Router) {}

  login() {
    const success = this.auth.login(this.username, this.password);

    if (success) {
      console.log('Login successful');
      this.loginSuccess.emit(this.username);
      this.router.navigate(['/movies']);
    } else {
      alert('Login failed: check username/password');
    }
  }
}
