import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import type {
  ActivityResponse,
  ActivityResultResponse,
  RegisterActivityResultRequest,
} from '@lectoapp-frontend-angular/models';
import type { Observable } from 'rxjs';

import { API_BASE_URL } from '../config/api-base-url.token';

@Injectable({
  providedIn: 'root',
})
export class ActivityApiService {
  private readonly http = inject(HttpClient);
  private readonly apiBaseUrl = inject(API_BASE_URL);

  getActivity(activityId: number): Observable<ActivityResponse> {
    return this.http.get<ActivityResponse>(
      `${this.apiBaseUrl}/api/actividades/${activityId}`,
    );
  }

  registerResult(
    request: RegisterActivityResultRequest,
  ): Observable<ActivityResultResponse> {
    return this.http.post<ActivityResultResponse>(
      `${this.apiBaseUrl}/api/estudiante/actividades/resultados`,
      request,
    );
  }
}