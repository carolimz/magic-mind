import { DatePipe } from '@angular/common';
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
  StudentApiService,
} from '@lectoapp-frontend-angular/api';
import {
  AuthSessionService,
} from '@lectoapp-frontend-angular/auth';
import type {
  CreateStudentRequest,
  StudentResponse,
} from '@lectoapp-frontend-angular/models';
import { ButtonModule } from '@openng/optimus-ui/button';
import { finalize } from 'rxjs';

import {
  StudentFormDialog,
} from '../../../components/student-form-dialog/student-form-dialog';

@Component({
  selector: 'app-student-list-page',
  imports: [
    DatePipe,
    ButtonModule,
    StudentFormDialog,
  ],
  templateUrl: './student-list-page.html',
  styleUrl: './student-list-page.scss',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class StudentListPage implements OnInit {
  private readonly studentApi =
    inject(StudentApiService);

  private readonly sessionService =
    inject(AuthSessionService);

  private readonly router =
    inject(Router);

  readonly students =
    signal<StudentResponse[]>([]);

  readonly loading = signal(true);
  readonly saving = signal(false);

  readonly deactivatingId =
    signal<number | null>(null);

  readonly formVisible = signal(false);

  readonly errorMessage =
    signal<string | null>(null);

  readonly successMessage =
    signal<string | null>(null);

  readonly copiedCode =
    signal<string | null>(null);

  readonly teacherName = computed(
    () =>
      this.sessionService.fullName() ??
      'Docente',
  );

  readonly activeStudents = computed(
    () =>
      this.students().filter(
        (student) => student.activo,
      ),
  );

  readonly inactiveStudents = computed(
    () =>
      this.students().filter(
        (student) => !student.activo,
      ),
  );

  ngOnInit(): void {
    this.loadStudents();
  }

  loadStudents(): void {
    this.loading.set(true);
    this.errorMessage.set(null);

    this.studentApi
      .getStudents()
      .pipe(
        finalize(() =>
          this.loading.set(false),
        ),
      )
      .subscribe({
        next: (students) => {
          this.students.set(students);
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

  createStudent(
    request: CreateStudentRequest,
  ): void {
    if (this.saving()) {
      return;
    }

    this.saving.set(true);
    this.errorMessage.set(null);
    this.successMessage.set(null);

    this.studentApi
      .createStudent(request)
      .pipe(
        finalize(() =>
          this.saving.set(false),
        ),
      )
      .subscribe({
        next: (student) => {
          this.students.update(
            (students) => [
              student,
              ...students,
            ],
          );

          this.formVisible.set(false);

          this.successMessage.set(
            `Estudiante creado. Su código es ${student.codigoAcceso}.`,
          );
        },
        error: (error: unknown) => {
          this.handleError(error);
        },
      });
  }

  deactivateStudent(
    student: StudentResponse,
  ): void {
    if (
      !student.activo ||
      this.deactivatingId() !== null
    ) {
      return;
    }

    const confirmed = window.confirm(
      `¿Deseas desactivar a ${student.nombre} ${student.apellido}?`,
    );

    if (!confirmed) {
      return;
    }

    this.deactivatingId.set(student.id);
    this.errorMessage.set(null);
    this.successMessage.set(null);

    this.studentApi
      .deactivateStudent(student.id)
      .pipe(
        finalize(() =>
          this.deactivatingId.set(null),
        ),
      )
      .subscribe({
        next: () => {
          this.students.update(
            (students) =>
              students.map(
                (currentStudent) =>
                  currentStudent.id === student.id
                    ? {
                        ...currentStudent,
                        activo: false,
                      }
                    : currentStudent,
              ),
          );

          this.successMessage.set(
            `${student.nombre} fue desactivado correctamente.`,
          );
        },
        error: (error: unknown) => {
          this.handleError(error);
        },
      });
  }

  copyAccessCode(code: string): void {
    void navigator.clipboard
      .writeText(code)
      .then(() => {
        this.copiedCode.set(code);

        window.setTimeout(() => {
          if (this.copiedCode() === code) {
            this.copiedCode.set(null);
          }
        }, 1800);
      })
      .catch(() => {
        this.errorMessage.set(
          'No fue posible copiar el código.',
        );
      });
  }

  openStudent(
    student: StudentResponse,
  ): void {
    void this.router.navigate([
      '/teacher/students',
      student.id,
    ]);
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
        '/login',
      );

      return;
    }

    if (
      error instanceof HttpErrorResponse &&
      error.status === 403
    ) {
      this.errorMessage.set(
        'No tienes permisos para realizar esta operación.',
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