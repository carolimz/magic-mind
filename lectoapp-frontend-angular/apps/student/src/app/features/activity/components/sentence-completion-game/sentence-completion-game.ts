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
  selector: 'app-sentence-completion-game',
  imports: [ButtonModule],
  templateUrl: './sentence-completion-game.html',
  styleUrl: './sentence-completion-game.scss',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class SentenceCompletionGame implements OnDestroy {
  readonly item = input.required<SelectionItem>();

  readonly selectedAnswer = input<string | null>(null);

  readonly lastItem = input(false);

  readonly submitting = input(false);

  readonly answerSelected = output<string>();

  readonly continueRequested = output<void>();

  private speechTimer:
    ReturnType<typeof setTimeout> | null = null;

  readonly sentenceParts = computed(() => {
    const parts =
      this.item().pregunta.split(/_{2,}/);

    return {
      before: parts[0]?.trim() ?? '',
      after: parts[1]?.trim() ?? '',
    };
  });

  readonly resourceEmoji = computed(() => {
    const resource =
      this.item().recurso
        ?.trim()
        .toLowerCase() ?? '';

    const emojis: Record<string, string> = {
      'perro_parque.png': '🐕',
      'manzana.png': '🍎',
      'pez.png': '🐟',
      'pajaro.png': '🐦',
      'vaca.png': '🐄',
      'nino_durmiendo.png': '😴',
      'rana.png': '🐸',
      'mama_cocinando.png': '👩‍🍳',
    };

    return emojis[resource] ?? '🖼️';
  });

  constructor() {
    effect(() => {
      // Cancelar cualquier speech anterior cuando cambia el ítem
      this.item();

      if (this.speechTimer) {
        clearTimeout(this.speechTimer);
      }

      if ('speechSynthesis' in window) {
        window.speechSynthesis.cancel();
      }
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

    const spokenSentence =
      this.item().pregunta.replace(
        /_{2,}/,
        ', ',
      );

    const instruction =
      new SpeechSynthesisUtterance(
        `Completa la frase. ${spokenSentence}`,
      );

    instruction.lang = 'es-CO';
    instruction.rate = 0.75;
    instruction.pitch = 1.05;
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