import { provideHttpClient } from '@angular/common/http';
import {
  HttpTestingController,
  provideHttpClientTesting,
} from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';
import type {
  ActivityResponse,
  ActivityResultResponse,
  RegisterActivityResultRequest,
} from '@lectoapp-frontend-angular/models';
import { afterEach, beforeEach, describe, expect, it } from 'vitest';

import { API_BASE_URL } from '../config/api-base-url.token';
import { ActivityApiService } from './activity-api.service';

describe('ActivityApiService', () => {
  const apiBaseUrl = 'http://localhost:8080';

  let service: ActivityApiService;
  let httpTesting: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        ActivityApiService,
        provideHttpClient(),
        provideHttpClientTesting(),
        {
          provide: API_BASE_URL,
          useValue: apiBaseUrl,
        },
      ],
    });

    service = TestBed.inject(ActivityApiService);
    httpTesting = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpTesting.verify();
  });

  it('consulta una actividad por su identificador', () => {
    const response: ActivityResponse = {
      id: 1,
      nombre: 'Ubica el objeto',
      descripcion: 'Identifica la ubicación espacial indicada.',
      tipoActividad: 'SELECCION',
      dificultad: 'FACIL',
      configuracion: {
        items: [
          {
            id: 1,
            recurso: 'gato.png',
            opciones: [
              'Arriba',
              'Abajo',
              'Izquierda',
              'Derecha',
            ],
            pregunta: '¿Dónde está el gato?',
            respuestaCorrecta: 'Arriba',
          },
        ],
      },
      etapaId: 1,
    };

    service.getActivity(1).subscribe((result) => {
      expect(result).toEqual(response);
    });

    const request = httpTesting.expectOne(
      `${apiBaseUrl}/api/actividades/1`,
    );

    expect(request.request.method).toBe('GET');

    request.flush(response);
  });

  it('registra el resultado de una actividad', () => {
    const requestBody: RegisterActivityResultRequest = {
      actividadId: 1,
      duracionSegundos: 35,
      respuestas: [
        {
          itemId: 1,
          respuesta: 'Arriba',
        },
      ],
    };

    const response: ActivityResultResponse = {
      id: 10,
      actividadId: 1,
      puntaje: 100,
      cantidadCorrectas: 1,
      cantidadIncorrectas: 0,
      porcentaje: 100,
      duracionSegundos: 35,
      estado: 'COMPLETADA',
      fechaRealizacion: '2026-08-10T10:00:00',
      errores: [],
      mensaje: null,
    };

    service.registerResult(requestBody).subscribe((result) => {
      expect(result).toEqual(response);
    });

    const request = httpTesting.expectOne(
      `${apiBaseUrl}/api/estudiante/actividades/resultados`,
    );

    expect(request.request.method).toBe('POST');
    expect(request.request.body).toEqual(requestBody);

    request.flush(response);
  });
});