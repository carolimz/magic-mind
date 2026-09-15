import {
  provideHttpClient,
  withInterceptors,
} from '@angular/common/http';
import {
  HttpTestingController,
  provideHttpClientTesting,
} from '@angular/common/http/testing';
import { HttpClient } from '@angular/common/http';
import { TestBed } from '@angular/core/testing';
import type { LoginResponse } from '@lectoapp-frontend-angular/models';
import { afterEach, beforeEach, describe, expect, it } from 'vitest';

import { AuthSessionService } from '../session/auth-session.service';
import { authTokenInterceptor } from './auth-token.interceptor';

describe('authTokenInterceptor', () => {
  let http: HttpClient;
  let httpTesting: HttpTestingController;
  let sessionService: AuthSessionService;

  const session: LoginResponse = {
    token: 'jwt-de-prueba',
    tipo: 'Bearer',
    nombre: 'Administrador',
    apellido: 'Sistema',
    correo: 'admin@lectoapp.com',
    rol: 'ADMIN',
  };

  beforeEach(() => {
    sessionStorage.clear();

    TestBed.configureTestingModule({
      providers: [
        provideHttpClient(
          withInterceptors([authTokenInterceptor]),
        ),
        provideHttpClientTesting(),
      ],
    });

    http = TestBed.inject(HttpClient);
    httpTesting = TestBed.inject(HttpTestingController);
    sessionService = TestBed.inject(AuthSessionService);
  });

  afterEach(() => {
    httpTesting.verify();
    sessionStorage.clear();
  });

  it('agrega el token a una petición protegida', () => {
    sessionService.saveSession(session);

    http.get('/api/etapas').subscribe();

    const request = httpTesting.expectOne('/api/etapas');

    expect(
      request.request.headers.get('Authorization'),
    ).toBe('Bearer jwt-de-prueba');

    request.flush([]);
  });

  it('no agrega autorización cuando no existe una sesión', () => {
    http.get('/api/etapas').subscribe();

    const request = httpTesting.expectOne('/api/etapas');

    expect(
      request.request.headers.has('Authorization'),
    ).toBe(false);

    request.flush([]);
  });

  it('no agrega el token al endpoint de login', () => {
    sessionService.saveSession(session);

    http.post('/auth/login', {}).subscribe();

    const request = httpTesting.expectOne('/auth/login');

    expect(
      request.request.headers.has('Authorization'),
    ).toBe(false);

    request.flush(session);
  });
});