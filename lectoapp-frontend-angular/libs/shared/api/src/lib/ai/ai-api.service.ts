import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import type { AiReportResponse } from '@lectoapp-frontend-angular/models';
import type { Observable } from 'rxjs';

import { API_BASE_URL } from '../config/api-base-url.token';

@Injectable({
    providedIn: 'root',
})
export class AiApiService {
    private readonly http = inject(HttpClient);
    private readonly apiBaseUrl = inject(API_BASE_URL);

    /**
     * Genera un reporte pedagógico con Gemini para el estudiante indicado.
     * El backend puede tardar varios segundos en responder.
     */
    generateStudentReport(
        studentId: number,
    ): Observable<AiReportResponse> {
        return this.http.get<AiReportResponse>(
            `${this.apiBaseUrl}/api/ai/reporte/estudiantes/${studentId}`,
        );
    }
}
