import { signal } from '@angular/core';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter } from '@angular/router';
import {
  AiApiService,
  StatisticsApiService,
  StudentApiService,
} from '@lectoapp-frontend-angular/api';
import { AuthSessionService } from '@lectoapp-frontend-angular/auth';
import { of } from 'rxjs';
import { beforeEach, describe, expect, it, vi } from 'vitest';
import { App } from './app';

describe('App (Teacher)', () => {
  let fixture: ComponentFixture<App>;

  const studentApiMock = {
    getStudents: vi.fn(() => of([])),
  };

  const statisticsApiMock = {
    getStudentOverview: vi.fn(() => of(null)),
    getStudentProgressByStage: vi.fn(() => of([])),
    getStudentFrequentErrors: vi.fn(() => of([])),
  };

  const aiApiMock = {
    generateStudentReport: vi.fn(() => of(null)),
  };

  const authSessionMock = {
    fullName: signal('Docente Tatiana'),
    clearSession: vi.fn(),
  };

  beforeEach(async () => {
    studentApiMock.getStudents.mockClear();
    authSessionMock.clearSession.mockClear();

    await TestBed.configureTestingModule({
      imports: [App],
      providers: [
        provideRouter([]),
        { provide: StudentApiService, useValue: studentApiMock },
        { provide: StatisticsApiService, useValue: statisticsApiMock },
        { provide: AiApiService, useValue: aiApiMock },
        { provide: AuthSessionService, useValue: authSessionMock },
      ],
    }).compileComponents();

    fixture = TestBed.createComponent(App);
  });

  it('debe crear la aplicación docente correctamente', () => {
    fixture.detectChanges();
    expect(fixture.componentInstance).toBeTruthy();
  });
});
