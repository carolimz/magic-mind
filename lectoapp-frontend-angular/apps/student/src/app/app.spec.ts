import { signal } from '@angular/core';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter } from '@angular/router';
import { LearningApiService } from '@lectoapp-frontend-angular/api';
import { AuthSessionService } from '@lectoapp-frontend-angular/auth';
import { of } from 'rxjs';
import { beforeEach, describe, expect, it, vi } from 'vitest';
import { App } from './app';

describe('App (Student)', () => {
  let fixture: ComponentFixture<App>;

  const learningApiMock = {
    getStudentLearningPath: vi.fn(() =>
      of({
        totalEtapas: 0,
        etapasCompletadas: 0,
        porcentajeCompletado: 0,
        etapas: [],
      }),
    ),
  };

  const authSessionMock = {
    session: signal({
      id: 1,
      nombre: 'Lucas',
      apellido: 'Pérez',
      rol: 'ESTUDIANTE',
    }),
    clearSession: vi.fn(),
  };

  beforeEach(async () => {
    learningApiMock.getStudentLearningPath.mockClear();
    authSessionMock.clearSession.mockClear();

    await TestBed.configureTestingModule({
      imports: [App],
      providers: [
        provideRouter([]),
        { provide: LearningApiService, useValue: learningApiMock },
        { provide: AuthSessionService, useValue: authSessionMock },
      ],
    }).compileComponents();

    fixture = TestBed.createComponent(App);
  });

  it('debe crear la aplicación del estudiante correctamente', () => {
    fixture.detectChanges();
    expect(fixture.componentInstance).toBeTruthy();
  });
});
