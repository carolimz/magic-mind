import {
  ChangeDetectionStrategy,
  Component,
  effect,
  input,
  OnDestroy,
  output,
} from '@angular/core';
import type { TrueFalseItem } from '@lectoapp-frontend-angular/models';
import { ButtonModule } from '@openng/optimus-ui/button';

@Component({
  selector: 'app-true-false-game',
  imports: [ButtonModule],
  templateUrl: './true-false-game.html',
  styleUrl: './true-false-game.scss',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class TrueFalseGame implements OnDestroy {
  readonly item = input.required<TrueFalseItem>();
  readonly selectedAnswer = input<boolean | null>(null);
  readonly lastItem = input(false);
  readonly submitting = input(false);

  readonly answerSelected = output<string>();
  readonly continueRequested = output<void>();

  private speechTimer: ReturnType<typeof setTimeout> | null = null;

  constructor() {
    effect(() => {
      this.item();

      if (this.speechTimer) {
        clearTimeout(this.speechTimer);
      }

      this.speechTimer = setTimeout(() => {
        this.speakInstruction();
      }, 300);
    });
  }

  selectAnswer(answer: boolean): void {
    if (this.submitting() || this.selectedAnswer() !== null) {
      return;
    }
    // We emit 'true' or 'false' as a string to conform with the generic selection logic
    this.answerSelected.emit(answer ? 'true' : 'false');
  }

  continue(): void {
    if (this.selectedAnswer() === null || this.submitting()) {
      return;
    }
    this.continueRequested.emit();
  }

  speakInstruction(): void {
    if (!('speechSynthesis' in window)) {
      return;
    }

    window.speechSynthesis.cancel();

    const instruction = new SpeechSynthesisUtterance(this.item().afirmacion);

    instruction.lang = 'es-CO';
    instruction.rate = 0.72;
    instruction.pitch = 1.1;
    instruction.volume = 1;

    window.speechSynthesis.speak(instruction);
  }

  ngOnDestroy(): void {
    if (this.speechTimer) {
      clearTimeout(this.speechTimer);
    }

    if ('speechSynthesis' in window) {
      window.speechSynthesis.cancel();
    }
  }
}
