import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import type {
  LoginResponse,
  StudentLoginRequest,
  UserLoginRequest,
} from '@lectoapp-frontend-angular/models';
import type { Observable } from 'rxjs';

import { API_BASE_URL } from '../config/api-base-url.token';

@Injectable({
  providedIn: 'root',
})
export class AuthApiService {
  private readonly http = inject(HttpClient);
  private readonly apiBaseUrl = inject(API_BASE_URL);

  login(request: UserLoginRequest): Observable<LoginResponse> {
    return this.http.post<LoginResponse>(
      `${this.apiBaseUrl}/auth/login`,
      request,
    );
  }

  loginStudent(request: StudentLoginRequest): Observable<LoginResponse> {
    return this.http.post<LoginResponse>(
      `${this.apiBaseUrl}/auth/estudiante/login`,
      request,
    );
  }
}