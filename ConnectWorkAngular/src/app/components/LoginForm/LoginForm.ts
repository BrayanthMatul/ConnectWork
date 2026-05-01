import { ChangeDetectionStrategy, Component, inject, signal } from '@angular/core';
import { LoginRequest, LoginServicio } from '../../services/LoginServicio';

@Component({
  selector: 'app-login-form',
  imports: [],
  templateUrl: './LoginForm.html',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class LoginForm {
  loginServicio = inject(LoginServicio);
  username = signal<string>('');
  password = signal<string>('');
  mensajeError = signal<string>('');

  onSubmit() {
    this.mensajeError.set('');
    const data: LoginRequest = {
      usernameOrEmail: this.username(),
      password: this.password(),
    };
    this.loginServicio.login(data).subscribe({
      next: () => {
        alert('Sesión iniciada');
      },
      error: (err) => {
        this.mensajeError.set(err);
      },
    });
  }
}
