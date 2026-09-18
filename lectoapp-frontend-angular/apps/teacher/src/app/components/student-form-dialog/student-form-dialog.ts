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
  CreateStudentRequest,
  StudentResponse,
} from '@lectoapp-frontend-angular/models';

interface StudentForm {
  nombre: FormControl<string>;
  apellido: FormControl<string>;
  fechaNacimiento: FormControl<string>;
}

@Component({
  selector: 'app-student-form-dialog',
  imports: [
    ReactiveFormsModule,
  ],
  templateUrl: './student-form-dialog.html',
  styleUrl: './student-form-dialog.scss',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class StudentFormDialog implements OnInit {
  readonly saving = input(false);
  readonly initialStudent = input<StudentResponse | null>(null);

  readonly submitted =
    output<CreateStudentRequest>();

  readonly closed = output<void>();

  readonly studentForm =
    new FormGroup<StudentForm>({
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

      fechaNacimiento: new FormControl('', {
        nonNullable: true,
        validators: [
          Validators.required,
        ],
      }),
    });

  ngOnInit(): void {
    const student = this.initialStudent();
    if (student) {
      this.studentForm.patchValue({
        nombre: student.nombre,
        apellido: student.apellido,
        fechaNacimiento: student.fechaNacimiento,
      });
    }
  }

  submit(): void {
    this.studentForm.markAllAsTouched();

    if (
      this.studentForm.invalid ||
      this.saving()
    ) {
      return;
    }

    this.submitted.emit({
      nombre:
        this.studentForm.controls
          .nombre.value.trim(),

      apellido:
        this.studentForm.controls
          .apellido.value.trim(),

      fechaNacimiento:
        this.studentForm.controls
          .fechaNacimiento.value,
    });
  }

  close(): void {
    if (this.saving()) {
      return;
    }

    this.closed.emit();
  }
}