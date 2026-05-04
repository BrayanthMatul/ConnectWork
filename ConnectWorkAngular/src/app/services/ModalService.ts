import { Injectable, inject } from '@angular/core';
import { Dialog } from '@angular/cdk/dialog';
import { Modal } from '../components/Modal/Modal';

export interface ModalConfig {
  titulo: string;
  mensaje: string;
}

@Injectable({ providedIn: 'root' })
export class ModalService {
  private dialog = inject(Dialog);

  /**
   * Abre un modal con título y mensaje
   */
  abrirModal(config: ModalConfig): void {
    this.dialog.open(Modal, {
      data: config,
      disableClose: true,
    });
  }

  /**
   * Abre un modal de éxito
   */
  abrirExito(mensaje: string): void {
    this.abrirModal({ titulo: 'Éxito', mensaje });
  }

  /**
   * Abre un modal de error
   */
  abrirError(mensaje: string): void {
    this.abrirModal({ titulo: 'Error', mensaje });
  }

  /**
   * Abre un modal de advertencia
   */
  abrirAdvertencia(mensaje: string): void {
    this.abrirModal({ titulo: 'Advertencia', mensaje });
  }

  /**
   * Abre un modal de información
   */
  abrirInfo(mensaje: string): void {
    this.abrirModal({ titulo: 'Información', mensaje });
  }
}
