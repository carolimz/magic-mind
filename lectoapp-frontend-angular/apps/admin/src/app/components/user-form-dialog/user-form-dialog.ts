import {
  ChangeDetectionStrategy,
  Component,
  input,
  OnInit,
  output,
} from '@angular/core';
import {
  FormControl,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import type {
  CreateUserRequest,
  UpdateUserRequest,
  UserResponse,
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
export class UserFormDialog implements OnInit {
  readonly saving = input(false);
  readonly initialUser = input<UserResponse | null>(null);

  readonly submitted =
    output<CreateUserRequest | UpdateUserRequest>();

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

  ngOnInit(): void {
    const user = this.initialUser();
    if (user) {
      this.userForm.patchValue({
        nombre: user.nombre,
        apellido: user.apellido,
        correo: user.correo,
        rol: user.rol,
      });

      this.userForm.controls.password.clearValidators();
      this.userForm.controls.password.updateValueAndValidity();
    }
  }

  submit(): void {
    this.userForm.markAllAsTouched();

    if (
      this.userForm.invalid ||
      this.saving()
    ) {
      return;
    }

    const payload = {
      nombre: this.userForm.controls.nombre.value.trim(),
      apellido: this.userForm.controls.apellido.value.trim(),
      correo: this.userForm.controls.correo.value.trim(),
      rol: this.userForm.controls.rol.value,
    };

    if (this.initialUser()) {
      this.submitted.emit(payload as UpdateUserRequest);
    } else {
      this.submitted.emit({
        ...payload,
        password: this.userForm.controls.password.value,
      } as CreateUserRequest);
    }
  }

  close(): void {
    if (!this.saving()) {
      this.closed.emit();
    }
  }
}