// ─── Estadísticas de Estudiante ───────────────────────────────────────────────

export interface StudentStatsSummaryResponse {
  estudianteId: number;
  nombreCompleto: string;
  actividadesRealizadas: number;
  promedioGeneral: number | null;
  porcentajeExito: number | null;
  nombreEtapa: string | null;
}

export interface StageProgressResponse {
  nombreEtapa: string;
  orden: number;
  actividadesCompletadas: number;
  totalActividades: number;
  porcentajeCompletado: number;
}

export interface FrequentErrorResponse {
  tipoError: string;
  frecuencia: number;
}

// ─── Reporte IA ───────────────────────────────────────────────────────────────

export interface AiReportResponse {
  estudianteId: number;
  nombreEstudiante: string;
  contenidoMarkdown: string;
  generadoEn: string;
}
