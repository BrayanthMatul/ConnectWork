import { ChangeDetectionStrategy, Component, inject, signal } from '@angular/core';
import { AuthService } from '../../services/AuthServicio';
import { LoginRequest } from '../../services/LoginServicio';

@Component({
  selector: 'app-login-form',
  imports: [],
  templateUrl: './LoginForm.html',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class LoginForm {
  username = signal<string>('');
  password = signal<string>('');
  mensajeError = signal<string>('');
  authService = inject(AuthService);

  onSubmit() {
    this.mensajeError.set('');
    const data: LoginRequest = {
      usernameOrEmail: this.username(),
      password: this.password(),
    };
    this.authService.login(data).subscribe({
      error: (error) => {
        this.mensajeError.set(error.error.valor);
      },
    });
  }
}
