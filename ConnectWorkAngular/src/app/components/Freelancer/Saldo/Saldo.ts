import { Component, inject, signal } from '@angular/core';
import { PerfilServicio } from '../../../services/PerfilServicio';
import { LoginServicio } from '../../../services/LoginServicio';

@Component({
  selector: 'app-saldo',
  imports: [],
  templateUrl: './Saldo.html',
})
export default class Saldo {
  protected saldo = signal<number>(0);
  private perfilServicio = inject(PerfilServicio);
  private loginServicio = inject(LoginServicio);

  ngOnInit() {
    this.cargarSaldo();
  }

  private cargarSaldo() {
    const usuario = this.loginServicio.getUsuario();
    if (!usuario) {
      console.error('No se pudo obtener el usuario logueado');
      return;
    }
    this.perfilServicio.obtenerSaldoPerfil(usuario.id).subscribe({
      next: (saldo) => {
        this.saldo.set(saldo);
      },
      error: (error) => {
        console.error('Error al obtener el saldo del perfil:', error);
      },
    });
  }
}
