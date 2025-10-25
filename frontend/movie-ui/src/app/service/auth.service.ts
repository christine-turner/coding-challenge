import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class AuthService {
  private credentialsSource = new BehaviorSubject<{ username: string; password: string } | null>(null);
  credentials$ = this.credentialsSource.asObservable();

  setCredentials(username: string, password: string) {
    this.credentialsSource.next({ username, password });
  }
}