import { Component, ChangeDetectionStrategy, input, output, effect } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AlphabetGridItem } from '@lectoapp-frontend-angular/models';

@Component({
  selector: 'app-alphabet-grid-game',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './alphabet-grid-game.html',
  styleUrls: ['./alphabet-grid-game.scss'],
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class AlphabetGridGameComponent {
  readonly item = input.required<AlphabetGridItem>();
  readonly onAnswer = output<string>();

  readonly alphabet = 'ABCDEFGHIJKLMNOPQRSTUVWXYZ'.split('');

  constructor() {
    effect(() => {
      const currentItem = this.item();
      if (currentItem && 'pregunta' in currentItem) {
        this.playAudio(currentItem.pregunta as string);
      }
    });
  }

  playAudio(text: string) {
    if ('speechSynthesis' in window) {
      window.speechSynthesis.cancel();
      const utterance = new SpeechSynthesisUtterance(text);
      utterance.lang = 'es-ES';
      utterance.rate = 0.9;
      window.speechSynthesis.speak(utterance);
    }
  }

  selectLetter(letter: string) {
    this.onAnswer.emit(letter);
  }
}
