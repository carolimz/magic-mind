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
import type { LoginResponse } from '@lectoapp-frontend-angular/models';
import { ButtonModule } from '@openng/optimus-ui/button';
import { InputTextModule } from '@openng/optimus-ui/inputtext';
import { finalize } from 'rxjs';

@Component({
  selector: 'app-login-page',
  imports: [
    ReactiveFormsModule,
    RouterLink,
    ButtonModule,
    InputTextModule,
  ],
  templateUrl: './login-page.html',
  styleUrl: './login-page.scss',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class LoginPage {
  private readonly formBuilder = inject(NonNullableFormBuilder);
  private readonly authApi = inject(AuthApiService);
  private readonly sessionService = inject(AuthSessionService);
  private readonly router = inject(Router);

  readonly loading = signal(false);
  readonly errorMessage = signal<string | null>(null);

  readonly studentForm = this.formBuilder.group({
    codigo: [
      '',
      [
        Validators.required,
        Validators.maxLength(10),
      ],
    ],
  });

  submitStudent(): void {
    if (this.studentForm.invalid || this.loading()) {
      this.studentForm.markAllAsTouched();
      return;
    }

    const request = {
      codigo: this.studentForm.controls.codigo.value
        .trim()
        .toUpperCase(),
    };

    this.loading.set(true);
    this.errorMessage.set(null);

    this.authApi
      .loginStudent(request)
      .pipe(finalize(() => this.loading.set(false)))
      .subscribe({
        next: (response) => this.completeLogin(response),
        error: (error: unknown) => this.handleError(error),
      });
  }

  private completeLogin(response: LoginResponse): void {
    this.sessionService.saveSession(response);
    void this.router.navigateByUrl('/student');
  }

  private handleError(error: unknown): void {
    if (error instanceof HttpErrorResponse) {
      const backendMessage =
        typeof error.error?.message === 'string'
          ? error.error.message
          : null;

      this.errorMessage.set(
        backendMessage ??
          'No encontramos ese código. Pide ayuda a tu profe.',
      );

      return;
    }

    this.errorMessage.set(
      'No pudimos entrar. Inténtalo otra vez.',
    );
  }
}