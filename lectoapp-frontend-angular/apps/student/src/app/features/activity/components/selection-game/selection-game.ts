import {
  ChangeDetectionStrategy,
  Component,
  input,
  output,
} from '@angular/core';
import type {
  SelectionItem,
} from '@lectoapp-frontend-angular/models';
import { ButtonModule } from '@openng/optimus-ui/button';

@Component({
  selector: 'app-selection-game',
  imports: [ButtonModule],
  templateUrl: './selection-game.html',
  styleUrl: './selection-game.scss',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class SelectionGame {
  readonly item = input.required<SelectionItem>();

  readonly selectedAnswer = input<string | null>(null);

  readonly lastItem = input(false);

  readonly submitting = input(false);

  readonly answerSelected = output<string>();

  readonly continueRequested = output<void>();

  selectAnswer(answer: string): void {
    if (this.submitting()) {
      return;
    }

    this.answerSelected.emit(answer);
  }

  continue(): void {
    if (
      !this.selectedAnswer() ||
      this.submitting()
    ) {
      return;
    }

    this.continueRequested.emit();
  }
}