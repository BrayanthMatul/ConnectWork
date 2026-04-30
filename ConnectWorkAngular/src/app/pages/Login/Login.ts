import { Component, inject, signal } from '@angular/core';
import { LoginServicio, LoginRequest } from '../../services/LoginServicio';

@Component({
  selector: 'app-login',
  imports: [],
  templateUrl: './Login.html',
  host: {
    class: 'flex-1 flex flex-col w-full',
  },
})
export class Login {
  username = signal<string>('');
  password = signal<string>('');

  loginServicio = inject(LoginServicio);

  onSubmit() {
    const data: LoginRequest = {
      username: this.username(),
      password: this.password()
    };
    this.loginServicio.login(data).subscribe({
      next: () => {
        alert('Sesión iniciada');
      },
      error: err => {
        alert('Error: ' + err);
      }
    });
  }
}
