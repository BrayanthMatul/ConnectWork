import { ChangeDetectionStrategy, Component } from '@angular/core';

@Component({
  selector: 'app-login',
  imports: [],
  templateUrl: './Login.html',
  host: {
    class: 'flex-1 flex flex-col w-full',
  },
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class Login { }
