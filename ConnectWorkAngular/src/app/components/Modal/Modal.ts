import { Component, ElementRef, ViewChild, input } from '@angular/core';

@Component({
  selector: 'app-modal',
  imports: [],
  templateUrl: './Modal.html',
})
export class Modal {
  mensaje = input.required<string>();

  @ViewChild('dialogElement') private nativeDialog!: ElementRef<HTMLDialogElement>;

  public abrir() {
    this.nativeDialog.nativeElement.showModal();
  }
}
