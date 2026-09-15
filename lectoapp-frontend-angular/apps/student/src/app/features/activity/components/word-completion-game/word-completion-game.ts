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

@Component({
  selector: 'app-word-completion-game',
  imports: [ButtonModule],
  templateUrl: './word-completion-game.html',
  styleUrl: './word-completion-game.scss',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class WordCompletionGame implements OnDestroy {
  readonly item = input.required<SelectionItem>();

  readonly selectedAnswer = input<string | null>(null);

  readonly lastItem = input(false);

  readonly submitting = input(false);

  readonly answerSelected = output<string>();

  readonly continueRequested = output<void>();

  private speechTimer:
    ReturnType<typeof setTimeout> | null = null;

  readonly wordPattern = computed(() => {
    const question = this.item().pregunta;

    const marker =
      question.toUpperCase().indexOf('PALABRA');

    if (marker < 0) {
      return question;
    }

    return question
      .slice(marker + 'PALABRA'.length)
      .trim();
  });

  readonly wordParts = computed(() =>
    this.wordPattern()
      .split(/\s+/)
      .filter((part) => part.length > 0),
  );

  readonly completedWord = computed(() =>
    this.wordPattern()
      .replace(
        '_',
        this.item().respuestaCorrecta,
      )
      .replace(/\s+/g, ''),
  );

  readonly isSyllableActivity = computed(
    () =>
      this.item().opciones.some(
        (option) =>
          Array.from(option.trim()).length > 1,
      ),
  );

  readonly activityInstruction = computed(
    () =>
      this.isSyllableActivity()
        ? 'Elige la sílaba'
        : 'Elige la letra',
  );

  readonly resourceEmoji = computed(() => {
    const resource =
      this.item().recurso
        ?.trim()
        .toLowerCase() ?? '';

    const emojis: Record<string, string> = {
      'casa.png': '🏠',
      'pato.png': '🦆',
      'mesa.png': '🪑',
      'sol.png': '☀️',
      'luna.png': '🌙',
      'gato.png': '🐱',
      'pera.png': '🍐',
      'moto.png': '🏍️',

      'plato.png': '🍽️',
      'bruja.png': '🧙‍♀️',
      'tren.png': '🚂',
      'fresa.png': '🍓',
      'blanco.png': '⚪',
      'globo.png': '🎈',
      'crema.png': '🧴',
      'fruta.png': '🍎',
    };

    return emojis[resource] ?? '🖼️';
  });

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

  speakInstruction(): void {
    if (!('speechSynthesis' in window)) {
      return;
    }

    window.speechSynthesis.cancel();

    const instruction =
      new SpeechSynthesisUtterance(
        `Completa la palabra ${this.completedWord()}`,
      );

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