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
} from '@angular/core';
import type {
  DragItem,
} from '@lectoapp-frontend-angular/models';
import { ButtonModule } from '@openng/optimus-ui/button';

@Component({
  selector: 'app-syllable-game',
  imports: [
    ButtonModule,
    DragDropModule,
  ],
  templateUrl: './syllable-game.html',
  styleUrl: './syllable-game.scss',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class SyllableGame implements OnDestroy {
  readonly item = input.required<DragItem>();

  readonly lastItem = input(false);

  readonly submitting = input(false);

  readonly answered = output<string>();

  readonly availableLetters = signal<string[]>([]);

  readonly assembledLetters = signal<string[]>([]);

  readonly dragging = signal(false);

  private draggingTimer:
    ReturnType<typeof setTimeout> | null = null;

  private speechTimer:
    ReturnType<typeof setTimeout> | null = null;

  constructor() {
    effect(() => {
      const currentItem = this.item();

      untracked(() => {
        this.prepareItem(currentItem);
      });
    });
  }

  dropLetter(
    event: CdkDragDrop<string[]>,
  ): void {
    if (this.submitting()) {
      return;
    }

    const available = [
      ...this.availableLetters(),
    ];

    const assembled = [
      ...this.assembledLetters(),
    ];

    const fromBank =
      event.previousContainer.id ===
      'letter-bank';

    const toBank =
      event.container.id ===
      'letter-bank';

    if (
      event.previousContainer.id ===
      event.container.id
    ) {
      const target = toBank
        ? available
        : assembled;

      moveItemInArray(
        target,
        event.previousIndex,
        event.currentIndex,
      );
    } else if (fromBank) {
      transferArrayItem(
        available,
        assembled,
        event.previousIndex,
        event.currentIndex,
      );
    } else {
      transferArrayItem(
        assembled,
        available,
        event.previousIndex,
        event.currentIndex,
      );
    }

    this.availableLetters.set(available);
    this.assembledLetters.set(assembled);
  }

  addLetter(index: number): void {
    if (
      this.dragging() ||
      this.submitting()
    ) {
      return;
    }

    const available = [
      ...this.availableLetters(),
    ];

    const assembled = [
      ...this.assembledLetters(),
    ];

    const selected = available.splice(
      index,
      1,
    )[0];

    if (selected === undefined) {
      return;
    }

    assembled.push(selected);

    this.availableLetters.set(available);
    this.assembledLetters.set(assembled);
  }

  removeLetter(index: number): void {
    if (
      this.dragging() ||
      this.submitting()
    ) {
      return;
    }

    const available = [
      ...this.availableLetters(),
    ];

    const assembled = [
      ...this.assembledLetters(),
    ];

    const selected = assembled.splice(
      index,
      1,
    )[0];

    if (selected === undefined) {
      return;
    }

    available.push(selected);

    this.availableLetters.set(available);
    this.assembledLetters.set(assembled);
  }

  startDragging(): void {
    this.dragging.set(true);
  }

  finishDragging(): void {
    if (this.draggingTimer) {
      clearTimeout(this.draggingTimer);
    }

    this.draggingTimer = setTimeout(() => {
      this.dragging.set(false);
    });
  }

  canConfirm(): boolean {
    return (
      this.assembledLetters().length ===
      this.item().respuestaCorrecta.length
    );
  }

  emptySlots(): number[] {
    const total =
      this.item().respuestaCorrecta.length;

    const occupied =
      this.assembledLetters().length;

    return Array.from(
      {
        length: Math.max(
          0,
          total - occupied,
        ),
      },
      (_, index) => index,
    );
  }

  confirm(): void {
    if (
      !this.canConfirm() ||
      this.submitting()
    ) {
      return;
    }

    this.answered.emit(
      this.assembledLetters().join(''),
    );
  }

  resetLetters(): void {
    this.prepareItem(this.item());
  }

  speakInstruction(): void {
    if (!('speechSynthesis' in window)) {
      return;
    }

    window.speechSynthesis.cancel();

    const instruction =
      new SpeechSynthesisUtterance(
        `Forma la sílaba ${this.item().respuestaCorrecta}`,
      );

    instruction.lang = 'es-CO';
    instruction.rate = 0.72;
    instruction.pitch = 1.1;
    instruction.volume = 1;

    window.speechSynthesis.speak(instruction);
  }

  ngOnDestroy(): void {
    if (this.draggingTimer) {
      clearTimeout(this.draggingTimer);
    }

    if (this.speechTimer) {
      clearTimeout(this.speechTimer);
    }

    if ('speechSynthesis' in window) {
      window.speechSynthesis.cancel();
    }
  }

  private prepareItem(
    item: DragItem,
  ): void {
    this.availableLetters.set(
      this.shuffleLetters(
        item.letras,
        item.respuestaCorrecta,
      ),
    );

    this.assembledLetters.set([]);

    if (this.speechTimer) {
      clearTimeout(this.speechTimer);
    }

    this.speechTimer = setTimeout(() => {
      this.speakInstruction();
    }, 300);
  }

  private shuffleLetters(
    source: string[],
    correctAnswer: string,
  ): string[] {
    const shuffled = [...source];

    for (
      let index = shuffled.length - 1;
      index > 0;
      index--
    ) {
      const randomIndex = Math.floor(
        Math.random() * (index + 1),
      );

      [
        shuffled[index],
        shuffled[randomIndex],
      ] = [
        shuffled[randomIndex],
        shuffled[index],
      ];
    }

    if (
      shuffled.length > 1 &&
      shuffled.join('') ===
        correctAnswer
    ) {
      [
        shuffled[0],
        shuffled[1],
      ] = [
        shuffled[1],
        shuffled[0],
      ];
    }

    return shuffled;
  }
}