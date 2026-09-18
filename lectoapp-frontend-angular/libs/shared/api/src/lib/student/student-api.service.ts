import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import type {
  ActivityResultResponse,
  CreateStudentRequest,
  ProgressSummaryResponse,
  ResumenEstadisticoEstudianteResponse,
  StudentResponse,
  UpdateStudentRequest,
} from '@lectoapp-frontend-angular/models';
import type { Observable } from 'rxjs';

import { API_BASE_URL } from '../config/api-base-url.token';

@Injectable({
  providedIn: 'root',
})
export class StudentApiService {
  private readonly http = inject(HttpClient);
  private readonly apiBaseUrl = inject(API_BASE_URL);

  getStudents(): Observable<StudentResponse[]> {
    return this.http.get<StudentResponse[]>(
      `${this.apiBaseUrl}/api/estudiantes`,
    );
  }

  getStudent(
    studentId: number,
  ): Observable<StudentResponse> {
    return this.http.get<StudentResponse>(
      `${this.apiBaseUrl}/api/estudiantes/${studentId}`,
    );
  }

  createStudent(
    request: CreateStudentRequest,
  ): Observable<StudentResponse> {
    return this.http.post<StudentResponse>(
      `${this.apiBaseUrl}/api/estudiantes`,
      request,
    );
  }

  updateStudent(
    studentId: number,
    request: UpdateStudentRequest,
  ): Observable<StudentResponse> {
    return this.http.put<StudentResponse>(
      `${this.apiBaseUrl}/api/estudiantes/${studentId}`,
      request,
    );
  }

  deactivateStudent(
    studentId: number,
  ): Observable<void> {
    return this.http.delete<void>(
      `${this.apiBaseUrl}/api/estudiantes/${studentId}`,
    );
  }

  getStudentResults(
    studentId: number,
  ): Observable<ActivityResultResponse[]> {
    return this.http.get<ActivityResultResponse[]>(
      `${this.apiBaseUrl}/api/docente/estudiantes/${studentId}/resultados`,
    );
  }

  getStudentProgressSummary(
    studentId: number,
  ): Observable<ProgressSummaryResponse> {
    return this.http.get<ProgressSummaryResponse>(
      `${this.apiBaseUrl}/api/docente/estudiantes/${studentId}/resumen`,
    );
  }

  getTeacherGlobalStatistics(): Observable<ResumenEstadisticoEstudianteResponse[]> {
    return this.http.get<ResumenEstadisticoEstudianteResponse[]>(
      `${this.apiBaseUrl}/api/estadisticas/docente/estudiantes`,
    );
  }
}