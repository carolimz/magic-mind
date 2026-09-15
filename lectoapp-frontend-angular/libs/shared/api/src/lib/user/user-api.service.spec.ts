import {
  provideHttpClient,
} from '@angular/common/http';
import {
  HttpTestingController,
  provideHttpClientTesting,
} from '@angular/common/http/testing';
import {
  TestBed,
} from '@angular/core/testing';
import type {
  UserRequest,
  UserResponse,
} from '@lectoapp-frontend-angular/models';
import {
  afterEach,
  beforeEach,
  describe,
  expect,
  it,
} from 'vitest';

import {
  API_BASE_URL,
} from '../config/api-base-url.token';
import {
  UserApiService,
} from './user-api.service';

describe('UserApiService', () => {
  const apiBaseUrl =
    'http://localhost:8080';

  let service: UserApiService;
  let httpTesting:
    HttpTestingController;

  const teacher: UserResponse = {
    id: 2,
    nombre: 'Laura',
    apellido: 'Gómez',
    correo: 'laura@lectoapp.com',
    rol: 'DOCENTE',
    activo: true,
  };

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        UserApiService,
        provideHttpClient(),
        provideHttpClientTesting(),
        {
          provide: API_BASE_URL,
          useValue: apiBaseUrl,
        },
      ],
    });

    service =
      TestBed.inject(UserApiService);

    httpTesting =
      TestBed.inject(
        HttpTestingController,
      );
  });

  afterEach(() => {
    httpTesting.verify();
  });

  it('lista los usuarios registrados', () => {
    service
      .getUsers()
      .subscribe((users) => {
        expect(users).toEqual([teacher]);
      });

    const request =
      httpTesting.expectOne(
        `${apiBaseUrl}/api/v1/usuarios`,
      );

    expect(
      request.request.method,
    ).toBe('GET');

    request.flush([teacher]);
  });

  it('consulta un usuario por su identificador', () => {
    service
      .getUser(teacher.id)
      .subscribe((user) => {
        expect(user).toEqual(teacher);
      });

    const request =
      httpTesting.expectOne(
        `${apiBaseUrl}/api/v1/usuarios/${teacher.id}`,
      );

    expect(
      request.request.method,
    ).toBe('GET');

    request.flush(teacher);
  });

  it('crea un usuario', () => {
    const requestBody: UserRequest = {
      nombre: 'Laura',
      apellido: 'Gómez',
      correo: 'laura@lectoapp.com',
      password: 'Temporal123',
      rol: 'DOCENTE',
    };

    service
      .createUser(requestBody)
      .subscribe((user) => {
        expect(user).toEqual(teacher);
      });

    const request =
      httpTesting.expectOne(
        `${apiBaseUrl}/api/v1/usuarios`,
      );

    expect(
      request.request.method,
    ).toBe('POST');

    expect(
      request.request.body,
    ).toEqual(requestBody);

    request.flush(teacher);
  });

  it('actualiza un usuario', () => {
    const requestBody: UserRequest = {
      nombre: 'Laura',
      apellido: 'Gómez',
      correo: 'laura@lectoapp.com',
      password: 'NuevaClave123',
      rol: 'DOCENTE',
    };

    service
      .updateUser(
        teacher.id,
        requestBody,
      )
      .subscribe((user) => {
        expect(user).toEqual(teacher);
      });

    const request =
      httpTesting.expectOne(
        `${apiBaseUrl}/api/v1/usuarios/${teacher.id}`,
      );

    expect(
      request.request.method,
    ).toBe('PUT');

    expect(
      request.request.body,
    ).toEqual(requestBody);

    request.flush(teacher);
  });

  it('elimina un usuario', () => {
    service
      .deleteUser(teacher.id)
      .subscribe((response) => {
        expect(response).toBeNull();
      });

    const request =
      httpTesting.expectOne(
        `${apiBaseUrl}/api/v1/usuarios/${teacher.id}`,
      );

    expect(
      request.request.method,
    ).toBe('DELETE');

    request.flush(null);
  });
});