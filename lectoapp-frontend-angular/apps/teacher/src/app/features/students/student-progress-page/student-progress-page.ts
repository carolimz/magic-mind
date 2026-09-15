import { DatePipe, DecimalPipe } from '@angular/common';
import { HttpErrorResponse } from '@angular/common/http';
import {
  ChangeDetectionStrategy,
  Component,
  computed,
  inject,
  OnInit,
  signal,
} from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import {
  AiApiService,
  StatisticsApiService,
  StudentApiService,
} from '@lectoapp-frontend-angular/api';
import { AuthSessionService } from '@lectoapp-frontend-angular/auth';
import type {
  ActivityResultResponse,
  AiReportResponse,
  FrequentErrorResponse,
  ProgressSummaryResponse,
  StageProgressResponse,
  StudentResponse,
  StudentStatsSummaryResponse,
} from '@lectoapp-frontend-angular/models';
import { finalize, forkJoin } from 'rxjs';

import {
  ProgressSummary,
} from '../../../components/progress-summary/progress-summary';
import {
  ResultHistory,
} from '../../../components/result-history/result-history';

@Component({
  selector: 'app-student-progress-page',
  imports: [DatePipe, DecimalPipe, ProgressSummary, ResultHistory],
  templateUrl: './student-progress-page.html',
  styleUrl: './student-progress-page.scss',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class StudentProgressPage implements OnInit {
  private readonly route = inject(ActivatedRoute);
  private readonly router = inject(Router);
  private readonly studentApi = inject(StudentApiService);
  private readonly statisticsApi = inject(StatisticsApiService);
  private readonly aiApi = inject(AiApiService);
  private readonly sessionService = inject(AuthSessionService);

  readonly student = signal<StudentResponse | null>(null);
  readonly summary = signal<ProgressSummaryResponse | null>(null);
  readonly results = signal<ActivityResultResponse[]>([]);
  readonly loading = signal(true);
  readonly errorMessage = signal<string | null>(null);

  readonly teacherName = computed(
    () => this.sessionService.fullName() ?? 'Docente Titular'
  );

  // ── Estadísticas nuevas ──────────────────────────────────────────────────────
  readonly statsSummary = signal<StudentStatsSummaryResponse | null>(null);
  readonly stageProgress = signal<StageProgressResponse[]>([]);
  readonly frequentErrors = signal<FrequentErrorResponse[]>([]);

  // ── Reporte IA ───────────────────────────────────────────────────────────────
  readonly showAiModal = signal(false);
  readonly aiLoading = signal(false);
  readonly aiReport = signal<AiReportResponse | null>(null);
  readonly aiError = signal<string | null>(null);

  ngOnInit(): void {
    const studentId = Number(
      this.route.snapshot.paramMap.get('studentId'),
    );

    if (!Number.isInteger(studentId) || studentId <= 0) {
      this.errorMessage.set('El estudiante solicitado no es válido.');
      this.loading.set(false);
      return;
    }

    this.loadProgress(studentId);
  }

  returnToStudents(): void {
    void this.router.navigateByUrl('/teacher');
  }

  retry(): void {
    const studentId = Number(
      this.route.snapshot.paramMap.get('studentId'),
    );
    if (Number.isInteger(studentId) && studentId > 0) {
      this.loadProgress(studentId);
    }
  }

  // ── Reporte IA ───────────────────────────────────────────────────────────────

  openAiReport(): void {
    this.showAiModal.set(true);

    // Si ya tenemos el reporte cargado, no volvemos a llamar a la API
    if (this.aiReport()) return;

    const studentId = Number(
      this.route.snapshot.paramMap.get('studentId'),
    );

    if (!Number.isInteger(studentId) || studentId <= 0) return;

    this.aiLoading.set(true);
    this.aiError.set(null);

    this.aiApi
      .generateStudentReport(studentId)
      .pipe(finalize(() => this.aiLoading.set(false)))
      .subscribe({
        next: (report) => this.aiReport.set(report),
        error: () =>
          this.aiError.set(
            'No pudimos generar el reporte en este momento. Inténtalo nuevamente.',
          ),
      });
  }

  closeAiModal(): void {
    this.showAiModal.set(false);
  }

  retryAiReport(): void {
    this.aiReport.set(null);
    this.openAiReport();
  }

  downloadPdf(): void {
    window.print();
  }

  onBackdropClick(event: MouseEvent): void {
    if (event.target === event.currentTarget) {
      this.closeAiModal();
    }
  }

  /** Convierte Markdown a HTML estructurado y elegante para el informe pedagógico. */
  markdownToHtml(md: string): string {
    if (!md) return '';

    // Convertir encabezados
    let html = md
      .replace(/^### (.*$)/gim, '<h3>$1</h3>')
      .replace(/^## (.*$)/gim, '<h2>$1</h2>')
      .replace(/^# (.*$)/gim, '<h1>$1</h1>');

    // Negrita e itálica
    html = html
      .replace(/\*\*(.*?)\*\*/gim, '<strong>$1</strong>')
      .replace(/\*(.*?)\*/gim, '<em>$1</em>');

    // Procesar listas con guión de forma agrupada
    html = html.replace(/^(?:- |\* )(.*$)/gim, '<li>$1</li>');
    html = html.replace(/((?:<li>.*?<\/li>\s*)+)/gis, '<ul>$1</ul>');

    // Párrafos para bloques de texto que no sean encabezados ni listas
    const blocks = html.split(/\n{2,}/);
    html = blocks
      .map((block) => {
        const trimmed = block.trim();
        if (!trimmed) return '';
        if (
          trimmed.startsWith('<h') ||
          trimmed.startsWith('<ul') ||
          trimmed.startsWith('<ol') ||
          trimmed.startsWith('<div')
        ) {
          return trimmed;
        }
        return `<p>${trimmed.replace(/\n/g, '<br>')}</p>`;
      })
      .filter(Boolean)
      .join('\n');

    return html;
  }

  // ── Carga de datos ───────────────────────────────────────────────────────────

  private loadProgress(studentId: number): void {
    this.loading.set(true);
    this.errorMessage.set(null);

    forkJoin({
      student: this.studentApi.getStudent(studentId),
      summary: this.studentApi.getStudentProgressSummary(studentId),
      results: this.studentApi.getStudentResults(studentId),
      statsSummary: this.statisticsApi.getStudentStatsSummary(studentId),
      stageProgress: this.statisticsApi.getStudentStageProgress(studentId),
      frequentErrors: this.statisticsApi.getStudentFrequentErrors(studentId),
    })
      .pipe(finalize(() => this.loading.set(false)))
      .subscribe({
        next: ({ student, summary, results, statsSummary, stageProgress, frequentErrors }) => {
          this.student.set(student);
          this.summary.set(summary);
          this.results.set(results);
          this.statsSummary.set(statsSummary);
          this.stageProgress.set(stageProgress);
          this.frequentErrors.set(frequentErrors);
        },
        error: (error: unknown) => this.handleError(error),
      });
  }

  private handleError(error: unknown): void {
    if (error instanceof HttpErrorResponse && error.status === 401) {
      this.sessionService.clearSession();
      void this.router.navigateByUrl('/login');
      return;
    }

    if (error instanceof HttpErrorResponse && error.status === 403) {
      this.errorMessage.set(
        'No tienes permisos para consultar este estudiante.',
      );
      return;
    }

    this.errorMessage.set(
      error instanceof HttpErrorResponse &&
        typeof error.error?.message === 'string'
        ? error.error.message
        : 'No pudimos cargar el progreso del estudiante.',
    );
  }
}