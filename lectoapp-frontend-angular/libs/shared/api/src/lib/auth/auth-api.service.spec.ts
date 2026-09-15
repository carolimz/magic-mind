import { provideHttpClient } from '@angular/common/http';
import {
  HttpTestingController,
  provideHttpClientTesting,
} from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';
import type {
  LoginResponse,
  StudentLoginRequest,
  UserLoginRequest,
} from '@lectoapp-frontend-angular/models';
import { afterEach, beforeEach, describe, expect, it } from 'vitest';

import { API_BASE_URL } from '../config/api-base-url.token';
import { AuthApiService } from './auth-api.service';

describe('AuthApiService', () => {
  const apiBaseUrl = 'http://localhost:8080';

  let service: AuthApiService;
  let httpTesting: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        AuthApiService,
        provideHttpClient(),
        provideHttpClientTesting(),
        {
          provide: API_BASE_URL,
          useValue: apiBaseUrl,
        },
      ],
    });

    service = TestBed.inject(AuthApiService);
    httpTesting = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpTesting.verify();
  });

  it('envía las credenciales del usuario al endpoint de login', () => {
    const request: UserLoginRequest = {
      correo: 'admin@lectoapp.com',
      password: 'password',
    };

    const response: LoginResponse = {
      token: 'jwt-admin',
      tipo: 'Bearer',
      nombre: 'Administrador',
      apellido: 'Sistema',
      correo: 'admin@lectoapp.com',
      rol: 'ADMIN',
    };

    service.login(request).subscribe((result) => {
      expect(result).toEqual(response);
    });

    const httpRequest = httpTesting.expectOne(
      `${apiBaseUrl}/auth/login`,
    );

    expect(httpRequest.request.method).toBe('POST');
    expect(httpRequest.request.body).toEqual(request);

    httpRequest.flush(response);
  });

  it('envía el código al endpoint de login del estudiante', () => {
    const request: StudentLoginRequest = {
      codigo: 'TETE',
    };

    const response: LoginResponse = {
      token: 'jwt-estudiante',
      tipo: 'Bearer',
      nombre: 'Test',
      apellido: 'Estudiante',
      correo: null,
      rol: 'ESTUDIANTE',
    };

    service.loginStudent(request).subscribe((result) => {
      expect(result).toEqual(response);
    });

    const httpRequest = httpTesting.expectOne(
      `${apiBaseUrl}/auth/estudiante/login`,
    );

    expect(httpRequest.request.method).toBe('POST');
    expect(httpRequest.request.body).toEqual(request);

    httpRequest.flush(response);
  });
});