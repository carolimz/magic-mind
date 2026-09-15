import {
  CdkDragDrop,
  DragDropModule,
  moveItemInArray,
  transferArrayItem,
} from '@angular/cdk/drag-drop';
import {
  ChangeDetectionStrategy,
  Component,
  effect,
  input,
  OnDestroy,
  output,
  signal,
  untracked,
  computed,
} from '@angular/core';
import type {
  SelectionItem,
} from '@lectoapp-frontend-angular/models';
import { ButtonModule } from '@openng/optimus-ui/button';

@Component({
  selector: 'app-story-sequence-game',
  imports: [
    ButtonModule,
    DragDropModule,
  ],
  templateUrl: './story-sequence-game.html',
  styleUrl: './story-sequence-game.scss',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class StorySequenceGame implements OnDestroy {
  readonly item = input.required<SelectionItem>();
  readonly lastItem = input(false);
  readonly submitting = input(false);

  readonly answered = output<string>();

  readonly availableImages = signal<string[]>([]);
  readonly timelineImages = signal<string[]>([]);

  readonly hasError = signal(false);

  private speechTimer: ReturnType<typeof setTimeout> | null = null;

  readonly resourceEmoji = computed(() => {
    return (filename: string) => {
      const emojis: Record<string, string> = {
        'liebre_corriendo.png': '🐇💨',
        'liebre_durmiendo.png': '🐇💤',
        'tortuga_ganando.png': '🐢🏆',
        'sembrando.png': '🌱',
        'regando.png': '🚿',
        'flor_crecida.png': '🌻',
        'mezclando.png': '🥣',
        'amasando.png': '🥖',
        'horneando.png': '🍪',
        'nubes.png': '☁️',
        'lluvia.png': '🌧️',
        'paraguas.png': '☂️'
      };
      return emojis[filename] ?? '🖼️';
    };
  });

  constructor() {
    effect(() => {
      const currentItem = this.item();
      untracked(() => {
        this.prepareItem(currentItem);
      });
    });
  }

  dropImage(event: CdkDragDrop<string[]>): void {
    if (this.submitting()) {
      return;
    }

    this.hasError.set(false);

    const available = [...this.availableImages()];
    const timeline = [...this.timelineImages()];

    const fromBank = event.previousContainer.id === 'image-bank';
    const toBank = event.container.id === 'image-bank';

    if (event.previousContainer.id === event.container.id) {
      const target = toBank ? available : timeline;
      moveItemInArray(target, event.previousIndex, event.currentIndex);
    } else if (fromBank) {
      transferArrayItem(available, timeline, event.previousIndex, event.currentIndex);
    } else {
      transferArrayItem(timeline, available, event.previousIndex, event.currentIndex);
    }

    this.availableImages.set(available);
    this.timelineImages.set(timeline);
  }

  canValidate(): boolean {
    return this.timelineImages().length === this.item().opciones.length;
  }

  validate(): void {
    if (!this.canValidate() || this.submitting()) {
      return;
    }

    const currentSequence = this.timelineImages().join(',');

    if (currentSequence === this.item().respuestaCorrecta) {
      this.answered.emit(currentSequence);
    } else {
      this.hasError.set(true);
      // Reset after a brief delay
      setTimeout(() => {
        this.prepareItem(this.item(), true);
      }, 1500);
    }
  }

  speakInstruction(isRetry = false): void {
    if (!('speechSynthesis' in window)) {
      return;
    }

    window.speechSynthesis.cancel();

    const prefix = isRetry 
      ? 'Intenta de nuevo. Escucha con atención: ' 
      : 'Escucha el cuento y ordena las imágenes: ';
    
    const instruction = new SpeechSynthesisUtterance(prefix + this.item().texto);

    instruction.lang = 'es-CO';
    instruction.rate = 0.72;
    instruction.pitch = 1.1;
    instruction.volume = 1;

    window.speechSynthesis.speak(instruction);
  }

  private prepareItem(item: SelectionItem, isRetry = false): void {
    this.hasError.set(false);
    this.availableImages.set(this.shuffleArray([...item.opciones]));
    this.timelineImages.set([]);

    if (this.speechTimer) {
      clearTimeout(this.speechTimer);
    }

    this.speechTimer = setTimeout(() => {
      this.speakInstruction(isRetry);
    }, 500);
  }

  private shuffleArray(array: string[]): string[] {
    const shuffled = [...array];
    for (let index = shuffled.length - 1; index > 0; index--) {
      const randomIndex = Math.floor(Math.random() * (index + 1));
      [shuffled[index], shuffled[randomIndex]] = [shuffled[randomIndex], shuffled[index]];
    }
    return shuffled;
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
