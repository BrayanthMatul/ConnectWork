import { DIALOG_DATA, DialogRef } from '@angular/cdk/dialog';
import { Component, Inject, inject, input } from '@angular/core';

@Component({
  selector: 'app-modal',
  imports: [],
  templateUrl: './Modal.html',
})
export class Modal {
  constructor(@Inject(DIALOG_DATA) public data: { titulo: string; mensaje: string }) {}

  private dialogRef = inject(DialogRef, { optional: true });

  protected cerrarModal() {
    this.dialogRef?.close();
  }
}
