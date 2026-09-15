import { provideHttpClient } from '@angular/common/http';
import {
  HttpTestingController,
  provideHttpClientTesting,
} from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';
import type { LearningPathResponse } from '@lectoapp-frontend-angular/models';
import { afterEach, beforeEach, describe, expect, it } from 'vitest';

import { API_BASE_URL } from '../config/api-base-url.token';
import { LearningApiService } from './learning-api.service';

describe('LearningApiService', () => {
  const apiBaseUrl = 'http://localhost:8080';

  let service: LearningApiService;
  let httpTesting: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        LearningApiService,
        provideHttpClient(),
        provideHttpClientTesting(),
        {
          provide: API_BASE_URL,
          useValue: apiBaseUrl,
        },
      ],
    });

    service = TestBed.inject(LearningApiService);
    httpTesting = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpTesting.verify();
  });

  it('consulta la ruta de aprendizaje del estudiante', () => {
    const response: LearningPathResponse = {
      porcentajeMinimoAprobacion: 70,
      etapasCompletadas: 1,
      totalEtapas: 9,
      rutaCompletada: false,
      etapas: [
        {
          etapaId: 1,
          nombre: 'Etapa 1 - Exploración espacial',
          descripcion: 'Descubre posiciones y direcciones básicas.',
          orden: 1,
          estado: 'COMPLETADA',
          actividadId: 1,
          actividadNombre: 'Ubica el objeto',
          tipoActividad: 'SELECCION',
          dificultad: 'FACIL',
        },
      ],
    };

    service.getStudentLearningPath().subscribe((result) => {
      expect(result).toEqual(response);
    });

    const request = httpTesting.expectOne(
      `${apiBaseUrl}/api/estudiante/ruta-aprendizaje`,
    );

    expect(request.request.method).toBe('GET');

    request.flush(response);
  });
});