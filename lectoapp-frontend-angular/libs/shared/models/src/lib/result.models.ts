export type PedagogicalErrorType =
  | 'CONFUSION_VOCAL'
  | 'CONFUSION_B_D'
  | 'CONFUSION_M_N'
  | 'CONFUSION_P_Q'
  | 'OMISION_LETRA'
  | 'OMISION_SILABA'
  | 'INVERSION_SILABA'
  | 'SUSTITUCION_CONSONANTE'
  | 'SUSTITUCION_VOCAL'
  | 'ERROR_COMPRENSION_LITERAL'
  | 'CONFUSION_DIRECCIONES'
  | 'OTRO';

export type ActivityResultStatus =
  | 'COMPLETADA'
  | 'TIEMPO_AGOTADO'
  | 'ABANDONADA';

export interface ActivityAnswerRequest {
  itemId: number;
  respuesta: string;
}

export interface RegisterActivityResultRequest {
  actividadId: number;
  duracionSegundos: number;
  respuestas: ActivityAnswerRequest[];
}

export interface DetectedErrorResponse {
  preguntaId: number;
  tipo: PedagogicalErrorType;
  respuestaEsperada: string;
  respuestaEstudiante: string;
}

export interface ActivityResultResponse {
  id: number;
  actividadId: number;
  puntaje: number;
  cantidadCorrectas: number;
  cantidadIncorrectas: number;
  porcentaje: number;
  duracionSegundos: number;
  estado: ActivityResultStatus;
  fechaRealizacion: string;
  errores: DetectedErrorResponse[];
  mensaje: string | null;
}

export interface ProgressSummaryResponse {
  actividadesRealizadas: number;
  totalCorrectas: number;
  totalIncorrectas: number;
  promedioPorcentaje: number;
  promedioPuntaje: number;
  ultimaActividad: string | null;
}