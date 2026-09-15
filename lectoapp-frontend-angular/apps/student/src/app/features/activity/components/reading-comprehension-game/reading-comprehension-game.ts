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
  selector: 'app-reading-comprehension-game',
  imports: [ButtonModule],
  templateUrl: './reading-comprehension-game.html',
  styleUrl: './reading-comprehension-game.scss',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class ReadingComprehensionGame implements OnDestroy {
  readonly item = input.required<SelectionItem>();

  readonly selectedAnswer =
    input<string | null>(null);

  readonly lastItem = input(false);

  readonly submitting = input(false);

  readonly answerSelected = output<string>();

  readonly continueRequested = output<void>();

  private speechTimer:
    ReturnType<typeof setTimeout> | null = null;

  readonly readingText = computed(
    () => this.item().texto?.trim() ?? '',
  );

  readonly resourceEmoji = computed(() => {
    const resource =
      this.item().recurso
        ?.trim()
        .toLowerCase() ?? '';

    const emojis: Record<string, string> = {
      'perrito_simon.png': '🐶',
      'colibri_nido.png': '🐦',
      'pastel_abuela.png': '🎂',
      'caracol_carrera.png': '🐌',
      'pirata_mapa.png': '🏴‍☠️',
      'oso_miel.png': '🐻',
      'robot_espacio.png': '🤖',
      'insectos_fiesta.png': '🦗',

      'cometa_viento.png': '🪁',
      'experimento_semilla.png': '🌱',
      'nubes_lluvia.png': '🌧️',
      'libro_magico.png': '📖',
      'gato_arbol.png': '🐈',
      'torta_horno.png': '🥧',
      'charco_lodo.png': '👢',
      'bufanda_lana.png': '🧣',
    };

    return emojis[resource] ?? '📚';
  });

  constructor() {
    effect(() => {
      // Cancelar speech anterior cuando cambia el ítem.
      // El dictador NO habla automáticamente: el niño debe
      // presionar el botón del altavoz para escuchar la lectura.
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

  speakReading(): void {
    if (!('speechSynthesis' in window)) {
      return;
    }

    window.speechSynthesis.cancel();

    const instruction =
      new SpeechSynthesisUtterance(
        `Escucha la lectura. ${this.readingText()}
        Ahora responde. ${this.item().pregunta}`,
      );

    instruction.lang = 'es-CO';
    instruction.rate = 0.76;
    instruction.pitch = 1.03;
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