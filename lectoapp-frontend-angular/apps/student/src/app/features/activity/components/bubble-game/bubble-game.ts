import {
  ChangeDetectionStrategy,
  Component,
  effect,
  input,
  OnDestroy,
  output,
  signal,
} from '@angular/core';
import type {
  SelectionItem,
} from '@lectoapp-frontend-angular/models';

import {
  FloatingBubbleDirective,
} from './floating-bubble.directive';

const POP_ANIMATION_DURATION = 500;
const SPEECH_DELAY = 250;

@Component({
  selector: 'app-bubble-game',
  imports: [FloatingBubbleDirective],
  templateUrl: './bubble-game.html',
  styleUrl: './bubble-game.scss',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class BubbleGame implements OnDestroy {
  readonly item = input.required<SelectionItem>();

  readonly disabled = input(false);

  readonly answered = output<string>();

  readonly poppingOption = signal<string | null>(null);

  private bubbleTimer: ReturnType<typeof setTimeout> | null = null;

  private speechTimer: ReturnType<typeof setTimeout> | null = null;

  constructor() {
    effect(() => {
      const currentItem = this.item();

      this.cancelSpeech();

      this.speechTimer = setTimeout(() => {
        this.speakInstruction(currentItem);
      }, SPEECH_DELAY);
    });
  }

  selectBubble(answer: string): void {
    if (
      this.poppingOption() !== null ||
      this.disabled()
    ) {
      return;
    }

    this.poppingOption.set(answer);

    this.bubbleTimer = setTimeout(() => {
      this.poppingOption.set(null);
      this.answered.emit(answer);
    }, POP_ANIMATION_DURATION);
  }

  speakInstruction(
    item: SelectionItem = this.item(),
  ): void {
    if (!('speechSynthesis' in window)) {
      return;
    }

    window.speechSynthesis.cancel();

    const instruction =
      new SpeechSynthesisUtterance(
        `Busca la letra ${item.respuestaCorrecta}`,
      );

    instruction.lang = 'es-CO';
    instruction.rate = 0.75;
    instruction.pitch = 1.15;
    instruction.volume = 1;

    window.speechSynthesis.speak(instruction);
  }

  ngOnDestroy(): void {
    if (this.bubbleTimer) {
      clearTimeout(this.bubbleTimer);
    }

    if (this.speechTimer) {
      clearTimeout(this.speechTimer);
    }

    this.cancelSpeech();
  }

  private cancelSpeech(): void {
    if ('speechSynthesis' in window) {
      window.speechSynthesis.cancel();
    }
  }
}