import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import type {
    FrequentErrorResponse,
    StageProgressResponse,
    StudentStatsSummaryResponse,
} from '@lectoapp-frontend-angular/models';
import type { Observable } from 'rxjs';

import { API_BASE_URL } from '../config/api-base-url.token';

@Injectable({
    providedIn: 'root',
})
export class StatisticsApiService {
    private readonly http = inject(HttpClient);
    private readonly apiBaseUrl = inject(API_BASE_URL);

    /** Resumen estadístico general de un estudiante (docente/admin). */
    getStudentStatsSummary(
        studentId: number,
    ): Observable<StudentStatsSummaryResponse> {
        return this.http.get<StudentStatsSummaryResponse>(
            `${this.apiBaseUrl}/api/estadisticas/estudiantes/${studentId}/resumen`,
        );
    }

    /** Progreso por etapas pedagógicas de un estudiante. */
    getStudentStageProgress(
        studentId: number,
    ): Observable<StageProgressResponse[]> {
        return this.http.get<StageProgressResponse[]>(
            `${this.apiBaseUrl}/api/estadisticas/estudiantes/${studentId}/progreso`,
        );
    }

    /** Errores pedagógicos más frecuentes del estudiante. */
    getStudentFrequentErrors(
        studentId: number,
        limit = 5,
    ): Observable<FrequentErrorResponse[]> {
        return this.http.get<FrequentErrorResponse[]>(
            `${this.apiBaseUrl}/api/estadisticas/estudiantes/${studentId}/errores`,
            { params: { limite: limit } },
        );
    }
}
