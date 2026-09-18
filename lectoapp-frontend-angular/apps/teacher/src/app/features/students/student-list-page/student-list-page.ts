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
  ResumenEstadisticoEstudianteResponse,
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

  readonly globalStats =
    signal<ResumenEstadisticoEstudianteResponse[]>([]);

  readonly loading = signal(true);
  readonly saving = signal(false);

  readonly deactivatingId =
    signal<number | null>(null);

  readonly editingStudent =
    signal<StudentResponse | null>(null);

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

  readonly stageDistribution = computed(() => {
    const stats = this.globalStats();
    let logografica = 0;
    let alfabetica = 0;
    let ortografica = 0;
    
    for (const stat of stats) {
      const etapa = (stat.nombreEtapa || '').toLowerCase();
      if (etapa.includes('logo')) logografica++;
      else if (etapa.includes('alfa')) alfabetica++;
      else if (etapa.includes('orto')) ortografica++;
    }

    const total = stats.length || 1;
    return {
      logografica,
      logograficaPct: Math.round((logografica / total) * 100),
      alfabetica,
      alfabeticaPct: Math.round((alfabetica / total) * 100),
      ortografica,
      ortograficaPct: Math.round((ortografica / total) * 100),
    };
  });

  readonly classAverageSuccess = computed(() => {
    const stats = this.globalStats();
    if (stats.length === 0) return 0;
    
    const sum = stats.reduce((acc, stat) => acc + (stat.porcentajeExito || 0), 0);
    return Math.round(sum / stats.length);
  });

  ngOnInit(): void {
    this.loadStudents();
  }

  loadStudents(): void {
    this.loading.set(true);
    this.errorMessage.set(null);

    this.studentApi
      .getStudents()
      .subscribe({
        next: (students) => {
          this.students.set(students);
          
          this.studentApi.getTeacherGlobalStatistics()
            .pipe(
              finalize(() => this.loading.set(false))
            )
            .subscribe({
              next: (stats) => this.globalStats.set(stats),
              error: (err) => {
                console.error('Error loading stats', err);
              }
            });
        },
        error: (error: unknown) => {
          this.loading.set(false);
          this.handleError(error);
        },
      });
  }

  showCreateForm(): void {
    this.errorMessage.set(null);
    this.successMessage.set(null);
    this.editingStudent.set(null);
    this.formVisible.set(true);
  }

  showEditForm(student: StudentResponse): void {
    this.errorMessage.set(null);
    this.successMessage.set(null);
    this.editingStudent.set(student);
    this.formVisible.set(true);
  }

  hideForm(): void {
    if (!this.saving()) {
      this.formVisible.set(false);
      this.editingStudent.set(null);
    }
  }

  saveStudent(
    request: CreateStudentRequest,
  ): void {
    if (this.saving()) {
      return;
    }

    this.saving.set(true);
    this.errorMessage.set(null);
    this.successMessage.set(null);

    const editing = this.editingStudent();
    const saveRequest = editing
      ? this.studentApi.updateStudent(editing.id, request)
      : this.studentApi.createStudent(request);

    saveRequest
      .pipe(
        finalize(() =>
          this.saving.set(false),
        ),
      )
      .subscribe({
        next: (student) => {
          this.students.update((students) => {
            if (editing) {
              return students.map((s) => (s.id === student.id ? student : s));
            }
            return [student, ...students];
          });

          this.formVisible.set(false);
          this.editingStudent.set(null);

          const action = editing ? 'actualizado' : 'creado';
          this.successMessage.set(
            `Estudiante ${action}. Su código es ${student.codigoAcceso}.`,
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