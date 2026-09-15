import {
  ChangeDetectionStrategy,
  Component,
  computed,
  inject,
} from '@angular/core';
import { Router } from '@angular/router';
import {
  AuthSessionService,
} from '@lectoapp-frontend-angular/auth';

import {
  UserListPage,
} from './features/users/user-list-page/user-list-page';

@Component({
  selector: 'app-root',
  imports: [
    UserListPage,
  ],
  templateUrl: './app.html',
  styleUrl: './app.scss',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class App {
  private readonly sessionService =
    inject(AuthSessionService);

  private readonly router =
    inject(Router);

  readonly administratorName = computed(
    () =>
      this.sessionService.fullName() ??
      'Administrador',
  );

  logout(): void {
    this.sessionService.clearSession();

    void this.router.navigateByUrl(
      '/login/personal',
    );
  }
}