import {
  ChangeDetectionStrategy,
  Component,
  computed,
  input,
  output,
} from '@angular/core';
import type {
  ActivityResultResponse,
} from '@lectoapp-frontend-angular/models';
import { ButtonModule } from '@openng/optimus-ui/button';

const PASS_PERCENTAGE = 70;

@Component({
  selector: 'app-activity-result',
  imports: [ButtonModule],
  templateUrl: './activity-result.html',
  styleUrl: './activity-result.scss',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class ActivityResult {
  readonly result =
    input.required<ActivityResultResponse>();

  readonly retryRequested = output<void>();
  readonly returnRequested = output<void>();

  readonly passed = computed(
    () =>
      this.result().porcentaje >=
      PASS_PERCENTAGE,
  );
}