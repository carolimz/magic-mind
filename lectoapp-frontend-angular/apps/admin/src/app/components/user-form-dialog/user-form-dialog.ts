import {
  ChangeDetectionStrategy,
  Component,
  input,
  output,
} from '@angular/core';
import {
  FormControl,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import type {
  UserRequest,
  UserRole,
} from '@lectoapp-frontend-angular/models';

interface UserForm {
  nombre: FormControl<string>;
  apellido: FormControl<string>;
  correo: FormControl<string>;
  password: FormControl<string>;
  rol: FormControl<UserRole>;
}

@Component({
  selector: 'app-user-form-dialog',
  imports: [
    ReactiveFormsModule,
  ],
  templateUrl: './user-form-dialog.html',
  styleUrl: './user-form-dialog.scss',
  changeDetection:
    ChangeDetectionStrategy.OnPush,
})
export class UserFormDialog {
  readonly saving = input(false);

  readonly submitted =
    output<UserRequest>();

  readonly closed = output<void>();

  readonly userForm =
    new FormGroup<UserForm>({
      nombre: new FormControl('', {
        nonNullable: true,
        validators: [
          Validators.required,
          Validators.maxLength(50),
        ],
      }),

      apellido: new FormControl('', {
        nonNullable: true,
        validators: [
          Validators.required,
          Validators.maxLength(50),
        ],
      }),

      correo: new FormControl('', {
        nonNullable: true,
        validators: [
          Validators.required,
          Validators.email,
          Validators.maxLength(100),
        ],
      }),

      password: new FormControl('', {
        nonNullable: true,
        validators: [
          Validators.required,
          Validators.minLength(8),
        ],
      }),

      rol: new FormControl<UserRole>(
        'DOCENTE',
        {
          nonNullable: true,
          validators: [
            Validators.required,
          ],
        },
      ),
    });

  submit(): void {
    this.userForm.markAllAsTouched();

    if (
      this.userForm.invalid ||
      this.saving()
    ) {
      return;
    }

    this.submitted.emit({
      nombre:
        this.userForm.controls
          .nombre.value.trim(),

      apellido:
        this.userForm.controls
          .apellido.value.trim(),

      correo:
        this.userForm.controls
          .correo.value.trim(),

      password:
        this.userForm.controls
          .password.value,

      rol:
        this.userForm.controls
          .rol.value,
    });
  }

  close(): void {
    if (!this.saving()) {
      this.closed.emit();
    }
  }
}