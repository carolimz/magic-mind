import { DecimalPipe } from '@angular/common';
import {
  ChangeDetectionStrategy,
  Component,
  computed,
  input,
} from '@angular/core';
import type {
  ActivityResultResponse,
  ProgressSummaryResponse,
} from '@lectoapp-frontend-angular/models';

@Component({
  selector: 'app-progress-summary',
  imports: [
    DecimalPipe,
  ],
  templateUrl: './progress-summary.html',
  styleUrl: './progress-summary.scss',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class ProgressSummary {
  readonly summary =
    input.required<ProgressSummaryResponse>();

  readonly results =
    input.required<ActivityResultResponse[]>();

  readonly totalCorrectAnswers = computed(
    () =>
      this.results().reduce(
        (total, result) =>
          total + result.cantidadCorrectas,
        0,
      ),
  );

  readonly totalIncorrectAnswers = computed(
    () =>
      this.results().reduce(
        (total, result) =>
          total + result.cantidadIncorrectas,
        0,
      ),
  );

  readonly totalDetectedErrors = computed(
    () =>
      this.results().reduce(
        (total, result) =>
          total + result.errores.length,
        0,
      ),
  );

  readonly bestPercentage = computed(() => {
    const results = this.results();

    if (results.length === 0) {
      return 0;
    }

    return Math.max(
      ...results.map(
        (result) => result.porcentaje,
      ),
    );
  });
}