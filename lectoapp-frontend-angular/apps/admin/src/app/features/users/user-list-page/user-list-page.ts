import { HttpErrorResponse } from '@angular/common/http';
import {
  ChangeDetectionStrategy,
  Component,
  computed,
  inject,
  OnInit,
  signal,
} from '@angular/core';
import { Router } from '@angular/router';
import {
  UserApiService,
} from '@lectoapp-frontend-angular/api';
import {
  AuthSessionService,
} from '@lectoapp-frontend-angular/auth';
import type {
  UserRequest,
  UserResponse,
} from '@lectoapp-frontend-angular/models';
import { ButtonModule } from '@openng/optimus-ui/button';
import { finalize } from 'rxjs';

import {
  UserCard,
} from '../../../components/user-card/user-card';
import {
  UserFormDialog,
} from '../../../components/user-form-dialog/user-form-dialog';

@Component({
  selector: 'app-user-list-page',
  imports: [
    ButtonModule,
    UserCard,
    UserFormDialog,
  ],
  templateUrl: './user-list-page.html',
  styleUrl: './user-list-page.scss',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class UserListPage implements OnInit {
  private readonly userApi =
    inject(UserApiService);

  private readonly sessionService =
    inject(AuthSessionService);

  private readonly router =
    inject(Router);

  readonly users =
    signal<UserResponse[]>([]);

  readonly loading = signal(true);
  readonly saving = signal(false);
  readonly formVisible = signal(false);

  readonly errorMessage =
    signal<string | null>(null);

  readonly successMessage =
    signal<string | null>(null);

  readonly administrators = computed(
    () =>
      this.users().filter(
        (user) =>
          user.rol === 'ADMIN',
      ),
  );

  readonly teachers = computed(
    () =>
      this.users().filter(
        (user) =>
          user.rol === 'DOCENTE',
      ),
  );

  readonly activeUsers = computed(
    () =>
      this.users().filter(
        (user) => user.activo,
      ).length,
  );

  ngOnInit(): void {
    this.loadUsers();
  }

  loadUsers(): void {
    this.loading.set(true);
    this.errorMessage.set(null);

    this.userApi
      .getUsers()
      .pipe(
        finalize(() =>
          this.loading.set(false),
        ),
      )
      .subscribe({
        next: (users) => {
          this.users.set(users);
        },

        error: (error: unknown) => {
          this.handleError(error);
        },
      });
  }

  showCreateForm(): void {
    this.errorMessage.set(null);
    this.successMessage.set(null);
    this.formVisible.set(true);
  }

  hideCreateForm(): void {
    if (!this.saving()) {
      this.formVisible.set(false);
    }
  }

  createUser(
    request: UserRequest,
  ): void {
    if (this.saving()) {
      return;
    }

    this.saving.set(true);
    this.errorMessage.set(null);
    this.successMessage.set(null);

    this.userApi
      .createUser(request)
      .pipe(
        finalize(() =>
          this.saving.set(false),
        ),
      )
      .subscribe({
        next: (user) => {
          this.users.update(
            (users) => [
              user,
              ...users,
            ],
          );

          this.formVisible.set(false);

          this.successMessage.set(
            `La cuenta de ${user.nombre} ${user.apellido} fue creada correctamente.`,
          );
        },

        error: (error: unknown) => {
          this.handleError(error);
        },
      });
  }

  private handleError(
    error: unknown,
  ): void {
    if (
      error instanceof HttpErrorResponse &&
      error.status === 401
    ) {
      this.sessionService.clearSession();

      void this.router.navigateByUrl(
        '/login/personal',
      );

      return;
    }

    if (
      error instanceof HttpErrorResponse &&
      error.status === 403
    ) {
      this.errorMessage.set(
        'No tienes permisos para administrar usuarios.',
      );

      return;
    }

    this.errorMessage.set(
      error instanceof HttpErrorResponse &&
        typeof error.error?.message ===
          'string'
        ? error.error.message
        : 'No pudimos completar la operación.',
    );
  }
}