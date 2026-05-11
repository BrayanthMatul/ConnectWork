import { Component, inject, signal } from '@angular/core';
import { ContratoServicio } from '../../../services/ContratoServicio';
import { LoginServicio } from '../../../services/LoginServicio';
import { CardContrato } from './CardContrato/CardContrato';
import { Contrato } from '../../../models/contrato';
import { EstadoContrato } from '../../../enums/estado-contrato';

@Component({
  selector: 'app-contratos-activos',
  imports: [CardContrato],
  templateUrl: './ContratosActivos.html',
})
export default class ContratosActivos {
  private contratoServicio = inject(ContratoServicio);
  private loginServicio = inject(LoginServicio);

  protected contratosActivos = signal<Contrato[]>([]);

  ngOnInit() {
    this.cargarContratosActivos();
  }

  private cargarContratosActivos(): void {
    this.contratoServicio.obtenerContratos().subscribe({
      next: (contratos) => {
        // Filtrar contratos activos (estado EN_PROGRESO)
        const contratosActivos = contratos.filter(
          (contrato) => contrato.estado === EstadoContrato.EN_PROGRESO,
        );
        this.contratosActivos.set(contratosActivos);
      },
      error: (error) => {
        console.error('Error al cargar los contratos', error);
      },
    });
  }
}
