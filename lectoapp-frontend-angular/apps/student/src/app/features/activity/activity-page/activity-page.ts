import { HttpErrorResponse } from '@angular/common/http';
import {
    ChangeDetectionStrategy,
    Component,
    computed,
    inject,
    OnInit,
    signal,
} from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ActivityApiService } from '@lectoapp-frontend-angular/api';
import { AuthSessionService } from '@lectoapp-frontend-angular/auth';
import type {
    ActivityItem,
    ActivityResponse,
    ActivityResultResponse,
    DragItem,
    MatchingItem,
    SelectionItem,
} from '@lectoapp-frontend-angular/models';
import { ButtonModule } from '@openng/optimus-ui/button';
import { finalize } from 'rxjs';

import {
    ActivityResult,
} from '../components/activity-result/activity-result';
import {
    BubbleGame,
} from '../components/bubble-game/bubble-game';
import {
    MemoryGame,
} from '../components/memory-game/memory-game';
import {
    ReadingComprehensionGame,
} from '../components/reading-comprehension-game/reading-comprehension-game';
import {
    SelectionGame,
} from '../components/selection-game/selection-game';
import {
    SentenceCompletionGame,
} from '../components/sentence-completion-game/sentence-completion-game';
import {
    SpatialDirectionGame,
} from '../components/spatial-direction-game/spatial-direction-game';
import {
    SyllableGame,
} from '../components/syllable-game/syllable-game';
import {
    WordCompletionGame,
} from '../components/word-completion-game/word-completion-game';
import {
    StorySequenceGame,
} from '../components/story-sequence-game/story-sequence-game';
import {
    TrueFalseGame,
} from '../components/true-false-game/true-false-game';
import {
    ShadowGame,
} from '../components/shadow-game/shadow-game';
import { SimonSaysGameComponent } from '../components/simon-says-game/simon-says-game';
import { AlphabetGridGameComponent } from '../components/alphabet-grid-game/alphabet-grid-game';

const SPATIAL_DIRECTION_STAGE = 1;
const WORD_COMPLETION_STAGE = 5;
const SENTENCE_COMPLETION_STAGE = 7;

const BUBBLE_STAGES = [2, 3];
const READING_STAGES = [8, 9];

@Component({
    selector: 'app-activity-page',
    imports: [
        ButtonModule,
        ActivityResult,
        BubbleGame,
        MemoryGame,
        ReadingComprehensionGame,
        SelectionGame,
        SentenceCompletionGame,
        SpatialDirectionGame,
        SyllableGame,
        WordCompletionGame,
        StorySequenceGame,
        TrueFalseGame,
        ShadowGame,
        SimonSaysGameComponent,
        AlphabetGridGameComponent,
    ],
    templateUrl: './activity-page.html',
    styleUrl: './activity-page.scss',
    changeDetection: ChangeDetectionStrategy.OnPush,
})
export class ActivityPage implements OnInit {
    private readonly route = inject(ActivatedRoute);

    private readonly router = inject(Router);

    private readonly activityApi =
        inject(ActivityApiService);

    private readonly sessionService =
        inject(AuthSessionService);

    private startedAt = Date.now();

    readonly activity =
        signal<ActivityResponse | null>(null);

    readonly result =
        signal<ActivityResultResponse | null>(null);

    readonly answers =
        signal<Record<number, string>>({});

    readonly currentIndex = signal(0);

    readonly loading = signal(true);

    readonly submitting = signal(false);

    readonly errorMessage =
        signal<string | null>(null);

    /** Número de intento actual. 1 = primer intento (silencio), 2+ = reintento (audio automático). */
    readonly attemptCount = signal(1);

    readonly currentItem =
        computed<ActivityItem | null>(() => {
            const currentActivity =
                this.activity();

            return (
                currentActivity?.configuracion.items[
                this.currentIndex()
                ] ?? null
            );
        });

    readonly progressPercentage =
        computed(() => {
            const currentActivity =
                this.activity();

            if (
                !currentActivity ||
                currentActivity.configuracion.items
                    .length === 0
            ) {
                return 0;
            }

            return Math.round(
                ((this.currentIndex() + 1) /
                    currentActivity.configuracion.items
                        .length) *
                100,
            );
        });

    readonly isLastItem = computed(() => {
        const currentActivity =
            this.activity();

        if (!currentActivity) {
            return false;
        }

        return (
            this.currentIndex() ===
            currentActivity.configuracion.items
                .length -
            1
        );
    });

    /**
     * Para el juego de memoria, todos los ítems de tipo MatchingItem
     * se presentan juntos en un solo tablero.
     */
    readonly matchingItems = computed<MatchingItem[]>(
        () => {
            const currentActivity = this.activity();
            if (!currentActivity) {
                return [];
            }
            return currentActivity.configuracion.items.filter(
                (item): item is MatchingItem => this.isMatchingItem(item),
            );
        },
    );

    ngOnInit(): void {
        const activityId = Number(
            this.route.snapshot.paramMap.get(
                'activityId',
            ),
        );

        if (
            !Number.isInteger(activityId) ||
            activityId <= 0
        ) {
            this.errorMessage.set(
                'La actividad solicitada no es válida.',
            );

            this.loading.set(false);

            return;
        }

        this.loadActivity(activityId);
    }

    loadActivity(
        activityId: number,
    ): void {
        this.loading.set(true);

        this.errorMessage.set(null);

        this.activityApi
            .getActivity(activityId)
            .pipe(
                finalize(() =>
                    this.loading.set(false),
                ),
            )
            .subscribe({
                next: (activity) => {
                    this.activity.set(activity);

                    this.startedAt = Date.now();
                },

                error: (error: unknown) =>
                    this.handleError(error),
            });
    }

    // ── Type guards ───────────────────────────────────────────────────────

    isSelectionItem(
        item: ActivityItem,
    ): item is SelectionItem {
        return (
            'opciones' in item &&
            'pregunta' in item
        );
    }

    isDragItem(
        item: ActivityItem,
    ): item is DragItem {
        return 'letras' in item;
    }

    isMatchingItem(
        item: ActivityItem,
    ): item is MatchingItem {
        return 'par1' in item && 'par2' in item;
    }

    isTrueFalseItem(
        item: ActivityItem,
    ): item is import('@lectoapp-frontend-angular/models').TrueFalseItem {
        return 'afirmacion' in item && 'esVerdadero' in item;
    }

    isShadowGameItem(
        item: ActivityItem,
    ): item is import('@lectoapp-frontend-angular/models').ShadowGameItem {
        return this.activity()?.tipoActividad === 'ADIVINA_LA_SOMBRA';
    }

    isSimonSaysItem(
        item: ActivityItem,
    ): item is SelectionItem {
        return this.activity()?.tipoActividad === 'SIMON_DICE';
    }

    isAlphabetGridItem(
        item: ActivityItem,
    ): item is import('@lectoapp-frontend-angular/models').AlphabetGridItem {
        return this.activity()?.tipoActividad === 'ALPHABET_GRID';
    }

    // ── Detectores de tipo de actividad ──────────────────────────────────

    isMemoryActivity(): boolean {
        return this.activity()?.tipoActividad === 'EMPAREJAR';
    }

    isStorySequenceItem(): boolean {
        return this.activity()?.tipoActividad === 'ORDENAR_SECUENCIA';
    }

    isSpatialDirectionItem(): boolean {
        return (
            Number(this.activity()?.etapaId) ===
            SPATIAL_DIRECTION_STAGE
        );
    }

    isReadingComprehensionItem(
        item: SelectionItem,
    ): boolean {
        const stageId = Number(
            this.activity()?.etapaId,
        );

        return (
            READING_STAGES.includes(stageId) ||
            Boolean(item.texto?.trim())
        );
    }

    isWordCompletionItem(
        item: SelectionItem,
    ): boolean {
        const stageId = Number(
            this.activity()?.etapaId,
        );

        const question =
            item.pregunta
                .trim()
                .toUpperCase();

        return (
            stageId === WORD_COMPLETION_STAGE ||
            question.includes(
                'COMPLETA LA PALABRA',
            )
        );
    }

    isSentenceCompletionItem(): boolean {
        return (
            Number(this.activity()?.etapaId) ===
            SENTENCE_COMPLETION_STAGE
        );
    }

    isLetterBubbleItem(
        item: SelectionItem,
    ): boolean {
        const stageId = Number(
            this.activity()?.etapaId,
        );

        return (
            BUBBLE_STAGES.includes(stageId) &&
            item.opciones.every(
                (option) =>
                    Array.from(
                        option.trim(),
                    ).length === 1,
            )
        );
    }

    // ── Manejo de respuestas ──────────────────────────────────────────────

    selectAnswer(
        itemId: number,
        answer: string,
    ): void {
        this.answers.update(
            (currentAnswers) => ({
                ...currentAnswers,

                [itemId]: answer,
            }),
        );
    }

    selectedAnswer(
        itemId: number,
    ): string | null {
        return (
            this.answers()[itemId] ?? null
        );
    }

    handleAutomaticAnswer(
        itemId: number,
        answer: string,
    ): void {
        if (this.submitting()) {
            return;
        }

        this.selectAnswer(
            itemId,
            answer,
        );

        this.continue();
    }

    /**
     * Llamado desde el juego de memoria cuando el niño encuentra TODAS las
     * parejas. Marca todos los ítems del tablero como correctamente respondidos
     * y envía el resultado al servidor.
     */
    handleMemoryCompleted(): void {
        if (this.submitting()) {
            return;
        }

        const currentActivity = this.activity();
        if (!currentActivity) {
            return;
        }

        // Marcar todos los ítems de memoria como "correctamente respondidos"
        const matchingItems = this.matchingItems();
        for (const item of matchingItems) {
            this.selectAnswer(item.id, item.par1);
        }

        this.submitResult();
    }

    continue(): void {
        const item = this.currentItem();

        if (
            !item ||
            !this.selectedAnswer(item.id)
        ) {
            return;
        }

        if (this.isLastItem()) {
            this.submitResult();

            return;
        }

        this.currentIndex.update(
            (index) => index + 1,
        );
    }

    retry(): void {
        this.result.set(null);

        this.answers.set({});

        this.currentIndex.set(0);

        this.errorMessage.set(null);

        this.startedAt = Date.now();

        // Incrementar intento: en el reintento se activa el audio automático
        this.attemptCount.update((n) => n + 1);
    }

    returnToPath(): void {
        void this.router.navigateByUrl(
            '/student',
        );
    }

    private submitResult(): void {
        const currentActivity =
            this.activity();

        if (
            !currentActivity ||
            this.submitting()
        ) {
            return;
        }

        const responses =
            currentActivity.configuracion.items.map(
                (item) => ({
                    itemId: item.id,

                    respuesta:
                        this.answers()[item.id] ?? item.id.toString(),
                }),
            );

        const durationSeconds = Math.max(
            1,

            Math.round(
                (Date.now() - this.startedAt) /
                1000,
            ),
        );

        this.submitting.set(true);

        this.errorMessage.set(null);

        this.activityApi
            .registerResult({
                actividadId:
                    currentActivity.id,

                duracionSegundos:
                    durationSeconds,

                respuestas: responses,
            })
            .pipe(
                finalize(() =>
                    this.submitting.set(false),
                ),
            )
            .subscribe({
                next: (result) =>
                    this.result.set(result),

                error: (error: unknown) =>
                    this.handleError(error),
            });
    }

    private handleError(
        error: unknown,
    ): void {
        if (
            error instanceof
            HttpErrorResponse &&
            error.status === 401
        ) {
            this.sessionService
                .clearSession();

            void this.router.navigateByUrl(
                '/login',
            );

            return;
        }

        this.errorMessage.set(
            error instanceof
                HttpErrorResponse &&
                typeof error.error?.message ===
                'string'
                ? error.error.message
                : 'No pudimos completar la actividad. Inténtalo otra vez.',
        );
    }
}
