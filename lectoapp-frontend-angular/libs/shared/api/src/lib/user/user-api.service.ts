import { HttpClient } from '@angular/common/http';
import {
  inject,
  Injectable,
} from '@angular/core';
import type {
  UserRequest,
  UserResponse,
} from '@lectoapp-frontend-angular/models';
import type {
  Observable,
} from 'rxjs';

import {
  API_BASE_URL,
} from '../config/api-base-url.token';

@Injectable({
  providedIn: 'root',
})
export class UserApiService {
  private readonly http =
    inject(HttpClient);

  private readonly apiBaseUrl =
    inject(API_BASE_URL);

  getUsers(): Observable<UserResponse[]> {
    return this.http.get<UserResponse[]>(
      `${this.apiBaseUrl}/api/v1/usuarios`,
    );
  }

  getUser(
    userId: number,
  ): Observable<UserResponse> {
    return this.http.get<UserResponse>(
      `${this.apiBaseUrl}/api/v1/usuarios/${userId}`,
    );
  }

  createUser(
    request: UserRequest,
  ): Observable<UserResponse> {
    return this.http.post<UserResponse>(
      `${this.apiBaseUrl}/api/v1/usuarios`,
      request,
    );
  }

  updateUser(
    userId: number,
    request: UserRequest,
  ): Observable<UserResponse> {
    return this.http.put<UserResponse>(
      `${this.apiBaseUrl}/api/v1/usuarios/${userId}`,
      request,
    );
  }

  deleteUser(
    userId: number,
  ): Observable<void> {
    return this.http.delete<void>(
      `${this.apiBaseUrl}/api/v1/usuarios/${userId}`,
    );
  }
}