import {
    ChangeDetectionStrategy,
    Component,
    computed,
    effect,
    input,
    OnDestroy,
    output,
    signal,
} from '@angular/core';
import type { MatchingItem } from '@lectoapp-frontend-angular/models';

/** Representa una carta individual en el tablero */
export interface MemoryCard {
    /** id único de esta carta en el tablero (se usa como track) */
    cardId: string;
    /** Id del par al que pertenece (para reconocer la pareja) */
    pairId: number;
    /** Texto/emoji que se muestra en la cara de la carta */
    value: string;
    /** true = mirando hacia arriba (visible) */
    flipped: boolean;
    /** true = pareja encontrada (no se vuelve a voltear) */
    matched: boolean;
}

const FLIP_BACK_DELAY_MS = 1100;
const SPEECH_DELAY_MS = 250;

@Component({
    selector: 'app-memory-game',
    templateUrl: './memory-game.html',
    styleUrl: './memory-game.scss',
    changeDetection: ChangeDetectionStrategy.OnPush,
})
export class MemoryGame implements OnDestroy {
    /** Lista de ítems-par que vienen del backend */
    readonly items = input.required<MatchingItem[]>();

    /** true cuando la actividad está siendo enviada al servidor */
    readonly submitting = input(false);

    /** true si es el último juego de la actividad */
    readonly lastItem = input(false);

    /** Se emite cuando el niño completó todas las parejas */
    readonly completed = output<void>();

    // ── Estado del tablero ──────────────────────────────────────────────────
    readonly cards = signal<MemoryCard[]>([]);

    /** Indice(s) de las cartas actualmente volteadas y aún sin validar */
    private readonly flippedIds = signal<string[]>([]);

    /** Indica si el juego está "bloqueado" mientras se comprueba una pareja */
    readonly locked = signal(false);

    // ── Computed ────────────────────────────────────────────────────────────
    readonly allMatched = computed(
        () =>
            this.cards().length > 0 &&
            this.cards().every((c) => c.matched),
    );

    readonly matchedCount = computed(
        () => this.cards().filter((c) => c.matched && c.cardId.endsWith('-a')).length,
    );

    readonly totalPairs = computed(
        () => this.items().length,
    );

    // ── Timers ──────────────────────────────────────────────────────────────
    private flipBackTimer: ReturnType<typeof setTimeout> | null = null;
    private speechTimer: ReturnType<typeof setTimeout> | null = null;

    constructor() {
        // Reinicializar el tablero cada vez que cambien los ítems
        effect(() => {
            const currentItems = this.items();
            this.buildBoard(currentItems);
        });
    }

    // ── Acciones del usuario ────────────────────────────────────────────────

    flipCard(card: MemoryCard): void {
        if (
            this.locked() ||
            card.flipped ||
            card.matched ||
            this.submitting()
        ) {
            return;
        }

        // Voltear la carta seleccionada
        this.updateCard(card.cardId, { flipped: true });

        // Pronunciar el valor si es una letra (longitud 1)
        if ([...card.value.trim()].length === 1) {
            this.speak(card.value);
        }

        const currentFlipped = [...this.flippedIds(), card.cardId];
        this.flippedIds.set(currentFlipped);

        if (currentFlipped.length === 2) {
            this.validatePair(currentFlipped[0], currentFlipped[1]);
        }
    }

    restart(): void {
        this.clearTimers();
        this.buildBoard(this.items());
    }

    continue(): void {
        if (!this.allMatched() || this.submitting()) {
            return;
        }
        this.completed.emit();
    }

    // ── Lógica interna ──────────────────────────────────────────────────────

    private validatePair(idA: string, idB: string): void {
        const cardA = this.cards().find((c) => c.cardId === idA);
        const cardB = this.cards().find((c) => c.cardId === idB);

        if (!cardA || !cardB) {
            return;
        }

        this.locked.set(true);

        if (cardA.pairId === cardB.pairId) {
            // ✅ Pareja correcta
            this.updateCard(idA, { matched: true });
            this.updateCard(idB, { matched: true });
            this.flippedIds.set([]);
            this.locked.set(false);
            this.speakSuccess();
        } else {
            // ❌ No son pareja — volver a voltear después de un delay
            this.flipBackTimer = setTimeout(() => {
                this.updateCard(idA, { flipped: false });
                this.updateCard(idB, { flipped: false });
                this.flippedIds.set([]);
                this.locked.set(false);
            }, FLIP_BACK_DELAY_MS);
        }
    }

    private updateCard(
        cardId: string,
        patch: Partial<MemoryCard>,
    ): void {
        this.cards.update((cards) =>
            cards.map((c) =>
                c.cardId === cardId ? { ...c, ...patch } : c,
            ),
        );
    }

    private buildBoard(items: MatchingItem[]): void {
        this.clearTimers();
        this.flippedIds.set([]);
        this.locked.set(false);

        // Crear dos cartas por par y mezclarlas
        const rawCards: MemoryCard[] = items.flatMap((item) => [
            {
                cardId: `${item.id}-a`,
                pairId: item.id,
                value: item.par1,
                flipped: false,
                matched: false,
            },
            {
                cardId: `${item.id}-b`,
                pairId: item.id,
                value: item.par2,
                flipped: false,
                matched: false,
            },
        ]);

        this.cards.set(this.shuffle(rawCards));
    }

    private shuffle<T>(array: T[]): T[] {
        const arr = [...array];
        for (let i = arr.length - 1; i > 0; i--) {
            const j = Math.floor(Math.random() * (i + 1));
            [arr[i], arr[j]] = [arr[j], arr[i]];
        }
        return arr;
    }

    // ── Voz ────────────────────────────────────────────────────────────────

    private speak(text: string): void {
        if (!('speechSynthesis' in window)) {
            return;
        }

        if (this.speechTimer) {
            clearTimeout(this.speechTimer);
        }

        this.speechTimer = setTimeout(() => {
            window.speechSynthesis.cancel();
            const utterance = new SpeechSynthesisUtterance(text);
            utterance.lang = 'es-CO';
            utterance.rate = 0.78;
            utterance.pitch = 1.1;
            utterance.volume = 1;
            window.speechSynthesis.speak(utterance);
        }, SPEECH_DELAY_MS);
    }

    private speakSuccess(): void {
        this.speak('¡Muy bien!');
    }

    // ── Limpieza ────────────────────────────────────────────────────────────

    private clearTimers(): void {
        if (this.flipBackTimer) {
            clearTimeout(this.flipBackTimer);
            this.flipBackTimer = null;
        }
        if (this.speechTimer) {
            clearTimeout(this.speechTimer);
            this.speechTimer = null;
        }
    }

    ngOnDestroy(): void {
        this.clearTimers();
        if ('speechSynthesis' in window) {
            window.speechSynthesis.cancel();
        }
    }
}
