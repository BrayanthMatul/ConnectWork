import { Component, inject, signal } from '@angular/core';
import { SaldoSistemaServicio } from '../../../../services/SaldoSistemaServicio';

@Component({
  selector: 'app-saldo-global',
  imports: [],
  templateUrl: './SaldoGlobal.html',
})
export default class SaldoGlobal {
  protected saldoGlobal = signal<number>(0);
  private saldoSistemaServicio = inject(SaldoSistemaServicio);

  ngOnInit() {
    this.cargarSaldoGlobal();
  }

  private cargarSaldoGlobal() {
    this.saldoSistemaServicio.obtenerTodosLosRegistros().subscribe({
      next: (response) => {
        this.saldoGlobal.set(response.totalSaldo);
      },
      error: (error) => {
        console.error('Error al cargar el saldo global:', error);
      },
    });
  }
}
