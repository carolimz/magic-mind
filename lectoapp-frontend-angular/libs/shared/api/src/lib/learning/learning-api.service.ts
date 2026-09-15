import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import type { LearningPathResponse } from '@lectoapp-frontend-angular/models';
import type { Observable } from 'rxjs';

import { API_BASE_URL } from '../config/api-base-url.token';

@Injectable({
  providedIn: 'root',
})
export class LearningApiService {
  private readonly http = inject(HttpClient);
  private readonly apiBaseUrl = inject(API_BASE_URL);

  getStudentLearningPath(): Observable<LearningPathResponse> {
    return this.http.get<LearningPathResponse>(
      `${this.apiBaseUrl}/api/estudiante/ruta-aprendizaje`,
    );
  }
}