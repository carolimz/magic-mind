import { Component, input, output, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SelectionItem } from '@lectoapp-frontend-angular/models';
import { ButtonModule } from '@openng/optimus-ui/button';

@Component({
  selector: 'app-simon-says-game',
  standalone: true,
  imports: [CommonModule, ButtonModule],
  templateUrl: './simon-says-game.html',
  styleUrls: ['./simon-says-game.scss'],
})
export class SimonSaysGameComponent {
  public readonly item = input.required<SelectionItem>();
  public readonly lastItem = input.required<boolean>();
  public readonly submitting = input.required<boolean>();

  public readonly complete = output<string>();

  public readonly selectedAnswer = signal<string | null>(null);

  public readonly showHint = signal<boolean>(false);

  // Se ejecuta desde la vista (ej: un botón) para escuchar la instrucción
  public listenInstruction(): void {
    if ('speechSynthesis' in window) {
      window.speechSynthesis.cancel();
      const utterance = new SpeechSynthesisUtterance(this.item().pregunta);
      utterance.lang = 'es-CO';
      utterance.rate = 0.76;
      utterance.pitch = 1.03;
      window.speechSynthesis.speak(utterance);
    }
    // Mostrar la pista después de escuchar
    this.showHint.set(true);
  }

  public selectAnswer(option: string): void {
    if (this.submitting()) return;
    this.selectedAnswer.set(option);
  }

  public continue(): void {
    const answer = this.selectedAnswer();
    if (!answer) return;
    this.complete.emit(answer);
  }
}
