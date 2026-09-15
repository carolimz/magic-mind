import {
  DatePipe,
  DecimalPipe,
} from '@angular/common';
import {
  ChangeDetectionStrategy,
  Component,
  input,
} from '@angular/core';
import type {
  ActivityResultResponse,
} from '@lectoapp-frontend-angular/models';

@Component({
  selector: 'app-result-history',
  imports: [
    DatePipe,
    DecimalPipe,
  ],
  templateUrl: './result-history.html',
  styleUrl: './result-history.scss',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class ResultHistory {
  readonly results =
    input.required<ActivityResultResponse[]>();

  isPassed(
    result: ActivityResultResponse,
  ): boolean {
    return result.porcentaje >= 70;
  }
}