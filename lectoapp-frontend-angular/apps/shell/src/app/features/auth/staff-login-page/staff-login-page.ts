import { HttpErrorResponse } from '@angular/common/http';
import {
  ChangeDetectionStrategy,
  Component,
  inject,
  signal,
} from '@angular/core';
import {
  NonNullableFormBuilder,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { AuthApiService } from '@lectoapp-frontend-angular/api';
import { AuthSessionService } from '@lectoapp-frontend-angular/auth';
import type {
  LoginResponse,
  UserRole,
} from '@lectoapp-frontend-angular/models';
import { ButtonModule } from '@openng/optimus-ui/button';
import { InputTextModule } from '@openng/optimus-ui/inputtext';
import { finalize } from 'rxjs';

@Component({
  selector: 'app-staff-login-page',
  imports: [
    ReactiveFormsModule,
    RouterLink,
    ButtonModule,
    InputTextModule,
  ],
  templateUrl: './staff-login-page.html',
  styleUrl: './staff-login-page.scss',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class StaffLoginPage {
  private readonly formBuilder = inject(NonNullableFormBuilder);
  private readonly authApi = inject(AuthApiService);
  private readonly sessionService = inject(AuthSessionService);
  private readonly router = inject(Router);

  readonly loading = signal(false);
  readonly errorMessage = signal<string | null>(null);

  readonly form = this.formBuilder.group({
    correo: [
      '',
      [
        Validators.required,
        Validators.email,
      ],
    ],
    password: ['', Validators.required],
  });

  submit(): void {
    if (this.form.invalid || this.loading()) {
      this.form.markAllAsTouched();
      return;
    }

    this.loading.set(true);
    this.errorMessage.set(null);

    this.authApi
      .login(this.form.getRawValue())
      .pipe(finalize(() => this.loading.set(false)))
      .subscribe({
        next: (response) => this.completeLogin(response),
        error: (error: unknown) => this.handleError(error),
      });
  }

  private completeLogin(response: LoginResponse): void {
    const destinations: Partial<Record<UserRole, string>> = {
      ADMIN: '/admin',
      DOCENTE: '/teacher',
    };

    const destination = destinations[response.rol];

    if (!destination) {
      this.errorMessage.set(
        'Este acceso es solamente para docentes y administradores.',
      );
      return;
    }

    this.sessionService.saveSession(response);
    void this.router.navigateByUrl(destination);
  }

  private handleError(error: unknown): void {
    if (error instanceof HttpErrorResponse) {
      this.errorMessage.set(
        typeof error.error?.message === 'string'
          ? error.error.message
          : 'Correo o contraseña incorrectos.',
      );

      return;
    }

    this.errorMessage.set(
      'No fue posible iniciar sesión.',
    );
  }
}