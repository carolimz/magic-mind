import {
  ChangeDetectionStrategy,
  Component,
  effect,
  input,
  OnDestroy,
  output,
  signal,
} from '@angular/core';
import type { ShadowGameItem } from '@lectoapp-frontend-angular/models';
import { ButtonModule } from '@openng/optimus-ui/button';

@Component({
  selector: 'app-shadow-game',
  imports: [ButtonModule],
  templateUrl: './shadow-game.html',
  styleUrl: './shadow-game.scss',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class ShadowGame implements OnDestroy {
  readonly item = input.required<ShadowGameItem>();
  readonly selectedAnswer = input<string | null>(null);
  readonly lastItem = input(false);
  readonly submitting = input(false);

  readonly answerSelected = output<string>();
  readonly continueRequested = output<void>();

  private speechTimer: ReturnType<typeof setTimeout> | null = null;
  readonly showColor = signal(false);

  constructor() {
    effect(() => {
      this.item();
      this.showColor.set(false);

      if (this.speechTimer) {
        clearTimeout(this.speechTimer);
      }

      this.speechTimer = setTimeout(() => {
        this.speakInstruction();
      }, 300);
    });
  }

  selectAnswer(answer: string): void {
    if (this.submitting() || this.selectedAnswer() !== null) {
      return;
    }
    this.showColor.set(true);
    this.answerSelected.emit(answer);
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
    const instruction = new SpeechSynthesisUtterance('¿Qué animal es esta sombra?');
    instruction.lang = 'es-CO';
    instruction.rate = 0.72;
    instruction.pitch = 1.1;
    window.speechSynthesis.speak(instruction);
  }

  ngOnDestroy(): void {
    if (this.speechTimer) clearTimeout(this.speechTimer);
    if ('speechSynthesis' in window) window.speechSynthesis.cancel();
  }
}
