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
import { LearningApiService } from '@lectoapp-frontend-angular/api';
import { AuthSessionService } from '@lectoapp-frontend-angular/auth';
import type {
  LearningPathResponse,
  LearningPathStage,
} from '@lectoapp-frontend-angular/models';
import { ButtonModule } from '@openng/optimus-ui/button';
import { finalize } from 'rxjs';

@Component({
  selector: 'app-root',
  imports: [ButtonModule],
  templateUrl: './app.html',
  styleUrl: './app.scss',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class App implements OnInit {
  private readonly learningApi = inject(LearningApiService);
  private readonly sessionService = inject(AuthSessionService);
  private readonly router = inject(Router);

  readonly learningPath = signal<LearningPathResponse | null>(null);
  readonly loading = signal(true);
  readonly errorMessage = signal<string | null>(null);

  readonly studentName = computed(
    () => this.sessionService.session()?.nombre ?? 'Pequeño lector',
  );

  readonly progressPercentage = computed(() => {
    const path = this.learningPath();

    if (!path || path.totalEtapas === 0) {
      return 0;
    }

    return Math.round(
      (path.etapasCompletadas / path.totalEtapas) * 100,
    );
  });

  ngOnInit(): void {
    this.loadLearningPath();
  }

  loadLearningPath(): void {
    this.loading.set(true);
    this.errorMessage.set(null);

    this.learningApi
      .getStudentLearningPath()
      .pipe(finalize(() => this.loading.set(false)))
      .subscribe({
        next: (response) => this.learningPath.set(response),
        error: (error: unknown) => this.handleError(error),
      });
  }

  startActivity(stage: LearningPathStage): void {
    if (stage.estado === 'BLOQUEADA') {
      return;
    }

    void this.router.navigate([
      '/student/activity',
      stage.actividadId,
    ]);
  }

  logout(): void {
    this.sessionService.clearSession();
    void this.router.navigateByUrl('/login');
  }

  stageIcon(stage: LearningPathStage): string {
    if (stage.estado === 'COMPLETADA') {
      return 'pi pi-check';
    }

    if (stage.estado === 'DISPONIBLE') {
      return 'pi pi-play';
    }

    return 'pi pi-lock';
  }

  stageNumber(stage: LearningPathStage): string {
    return String(stage.orden).padStart(2, '0');
  }

  private handleError(error: unknown): void {
    if (error instanceof HttpErrorResponse && error.status === 401) {
      this.sessionService.clearSession();
      void this.router.navigateByUrl('/login');
      return;
    }

    this.errorMessage.set(
      'No pudimos cargar tu aventura. Inténtalo nuevamente.',
    );
  }
}