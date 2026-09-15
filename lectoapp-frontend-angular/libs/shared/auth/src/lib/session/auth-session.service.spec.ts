import { TestBed } from '@angular/core/testing';
import type { LoginResponse } from '@lectoapp-frontend-angular/models';
import { afterEach, beforeEach, describe, expect, it } from 'vitest';

import { AuthSessionService } from './auth-session.service';

describe('AuthSessionService', () => {
  let service: AuthSessionService;

  const studentSession: LoginResponse = {
    token: 'jwt-estudiante',
    tipo: 'Bearer',
    nombre: 'Test',
    apellido: 'Estudiante',
    correo: null,
    rol: 'ESTUDIANTE',
  };

  beforeEach(() => {
    sessionStorage.clear();

    TestBed.configureTestingModule({});

    service = TestBed.inject(AuthSessionService);
  });

  afterEach(() => {
    sessionStorage.clear();
  });

  it('guarda la sesión autenticada', () => {
    service.saveSession(studentSession);

    expect(service.session()).toEqual(studentSession);
    expect(service.isAuthenticated()).toBe(true);
    expect(service.getToken()).toBe('jwt-estudiante');
    expect(service.role()).toBe('ESTUDIANTE');
    expect(service.fullName()).toBe('Test Estudiante');
  });

  it('comprueba el rol actual', () => {
    service.saveSession(studentSession);

    expect(service.hasRole('ESTUDIANTE')).toBe(true);
    expect(service.hasRole('ADMIN', 'DOCENTE')).toBe(false);
  });

  it('elimina la sesión', () => {
    service.saveSession(studentSession);
    service.clearSession();

    expect(service.session()).toBeNull();
    expect(service.isAuthenticated()).toBe(false);
    expect(service.getToken()).toBeNull();
    expect(sessionStorage.length).toBe(0);
  });
});