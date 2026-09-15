import {
  ChangeDetectionStrategy,
  Component,
  computed,
  effect,
  input,
  OnDestroy,
  output,
} from '@angular/core';
import type {
  SelectionItem,
} from '@lectoapp-frontend-angular/models';
import { ButtonModule } from '@openng/optimus-ui/button';

type Direction =
  | 'arriba'
  | 'abajo'
  | 'izquierda'
  | 'derecha';

@Component({
  selector: 'app-spatial-direction-game',
  imports: [ButtonModule],
  templateUrl: './spatial-direction-game.html',
  styleUrl: './spatial-direction-game.scss',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class SpatialDirectionGame implements OnDestroy {
  readonly item = input.required<SelectionItem>();

  readonly selectedAnswer =
    input<string | null>(null);

  readonly lastItem = input(false);

  readonly submitting = input(false);

  readonly answerSelected = output<string>();

  readonly continueRequested = output<void>();

  private speechTimer:
    ReturnType<typeof setTimeout> | null = null;

  readonly direction = computed<Direction>(() => {
    const answer =
      this.item().respuestaCorrecta
        .trim()
        .toLowerCase();

    if (
      answer === 'arriba' ||
      answer === 'abajo' ||
      answer === 'izquierda' ||
      answer === 'derecha'
    ) {
      return answer;
    }

    return 'arriba';
  });

  readonly isArrowExercise = computed(
    () =>
      this.item().recurso
        ?.toLowerCase()
        .startsWith('flecha_') ?? false,
  );

  readonly visualEmoji = computed(() => {
    const resource =
      this.item().recurso
        ?.trim()
        .toLowerCase() ?? '';

    const emojis: Record<string, string> = {
      'gato_arriba.png': '🐱',
      'pelota_abajo.png': '⚽',
      'estrella_izquierda.png': '⭐',
      'avion_derecha.png': '✈️',
    };

    return emojis[resource] ?? '🎈';
  });

  readonly arrowIcon = computed(() =>
    this.directionIcon(
      this.item().respuestaCorrecta,
    ),
  );

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

  selectOption(option: string): void {
    if (this.submitting()) {
      return;
    }

    this.answerSelected.emit(option);
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

  directionIcon(direction: string): string {
    const icons: Record<string, string> = {
      arriba: '⬆️',
      abajo: '⬇️',
      izquierda: '⬅️',
      derecha: '➡️',
    };

    return (
      icons[direction.trim().toLowerCase()] ??
      '❓'
    );
  }

  speakInstruction(): void {
    if (!('speechSynthesis' in window)) {
      return;
    }

    window.speechSynthesis.cancel();

    const instruction =
      new SpeechSynthesisUtterance(
        `Mira con atención. ${this.item().pregunta}`,
      );

    instruction.lang = 'es-CO';
    instruction.rate = 0.75;
    instruction.pitch = 1.08;
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