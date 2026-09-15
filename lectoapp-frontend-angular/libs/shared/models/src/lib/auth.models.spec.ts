import { describe, expect, it } from 'vitest';
import type { LoginResponse } from './auth.models';

describe('LoginResponse', () => {
  it('representa una sesión de estudiante', () => {
    const response: LoginResponse = {
      token: 'jwt-de-prueba',
      tipo: 'Bearer',
      nombre: 'Test',
      apellido: 'Estudiante',
      correo: null,
      rol: 'ESTUDIANTE',
    };

    expect(response.rol).toBe('ESTUDIANTE');
    expect(response.correo).toBeNull();
  });
});