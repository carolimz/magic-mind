import {
  signal,
} from '@angular/core';
import {
  ComponentFixture,
  TestBed,
} from '@angular/core/testing';
import {
  provideRouter,
} from '@angular/router';
import {
  UserApiService,
} from '@lectoapp-frontend-angular/api';
import {
  AuthSessionService,
} from '@lectoapp-frontend-angular/auth';
import {
  of,
} from 'rxjs';
import {
  beforeEach,
  describe,
  expect,
  it,
  vi,
} from 'vitest';

import {
  App,
} from './app';

describe('App', () => {
  let fixture:
    ComponentFixture<App>;

  const userApiMock = {
    getUsers: vi.fn(
      () => of([]),
    ),
  };

  const authSessionMock = {
    fullName: signal(
      'Administrador Sistema',
    ),
    clearSession: vi.fn(),
  };

  beforeEach(async () => {
    userApiMock.getUsers.mockClear();
    authSessionMock.clearSession.mockClear();

    await TestBed
      .configureTestingModule({
        imports: [
          App,
        ],
        providers: [
          provideRouter([]),
          {
            provide: UserApiService,
            useValue: userApiMock,
          },
          {
            provide: AuthSessionService,
            useValue: authSessionMock,
          },
        ],
      })
      .compileComponents();

    fixture =
      TestBed.createComponent(App);

    fixture.detectChanges();
  });

  it('crea la aplicación administrativa', () => {
    expect(
      fixture.componentInstance,
    ).toBeTruthy();
  });

  it('muestra la marca Magic Mind', () => {
    const element =
      fixture.nativeElement as HTMLElement;

    expect(
      element.textContent,
    ).toContain('Magic Mind');
  });

  it('muestra el nombre del administrador', () => {
    const element =
      fixture.nativeElement as HTMLElement;

    expect(
      element.textContent,
    ).toContain(
      'Administrador Sistema',
    );
  });

  it('consulta los usuarios al iniciar', () => {
    expect(
      userApiMock.getUsers,
    ).toHaveBeenCalledOnce();
  });
});