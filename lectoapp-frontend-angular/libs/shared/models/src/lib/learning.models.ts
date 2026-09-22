export type ActivityType =
    | 'SELECCION'
    | 'ARRASTRAR'
    | 'EMPAREJAR'
    | 'ORDENAR'
    | 'VERDADERO_FALSO'
    | 'ORDENAR_SECUENCIA'
    | 'ADIVINA_LA_SOMBRA';

export type ActivityDifficulty = 'FACIL' | 'MEDIA' | 'DIFICIL';

export type StageStatus = 'COMPLETADA' | 'DISPONIBLE' | 'BLOQUEADA';

export interface StageResponse {
    id: number;
    nombre: string;
    descripcion: string;
    orden: number;
}

export interface SelectionItem {
    id: number;
    texto?: string;
    recurso: string;
    opciones: string[];
    pregunta: string;
    respuestaCorrecta: string;
}

export interface DragItem {
    id: number;
    letras: string[];
    respuestaCorrecta: string;
}

/**
 * Par de cartas para el juego de memoria (tipo EMPAREJAR).
 *  - par1: letra mayúscula o consonante
 *  - par2: letra minúscula o emoji de imagen
 *  - tipo: 'LETRA_LETRA' = mayúscula/minúscula | 'LETRA_IMAGEN' = letra/emoji
 */
export interface MatchingItem {
    id: number;
    par1: string;
    par2: string;
    tipo: 'LETRA_LETRA' | 'LETRA_IMAGEN';
}

export interface TrueFalseItem {
    id: number;
    afirmacion: string;
    recurso?: string;
    esVerdadero: boolean;
}

export interface ShadowGameItem {
    id: number;
    opciones: string[];
    respuestaCorrecta: string;
    recurso: string;
}

export type ActivityItem = SelectionItem | DragItem | MatchingItem | TrueFalseItem | ShadowGameItem;

export interface ActivityConfiguration {
    items: ActivityItem[];
}

export interface ActivityResponse {
    id: number;
    nombre: string;
    descripcion: string;
    tipoActividad: ActivityType;
    dificultad: ActivityDifficulty;
    configuracion: ActivityConfiguration;
    etapaId: number;
}

export interface LearningPathStage {
    etapaId: number;
    nombre: string;
    descripcion: string;
    orden: number;
    estado: StageStatus;
    actividadId: number;
    actividadNombre: string;
    tipoActividad: ActivityType;
    dificultad: ActivityDifficulty;
}

export interface LearningPathResponse {
    porcentajeMinimoAprobacion: number;
    etapasCompletadas: number;
    totalEtapas: number;
    rutaCompletada: boolean;
    etapas: LearningPathStage[];
}
