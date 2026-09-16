import { loadRemoteModule } from '@angular-architects/native-federation';
import type { Routes } from '@angular/router';
import { roleGuard } from '@lectoapp-frontend-angular/auth';

export const appRoutes: Routes = [
  {
    path: 'login/personal',
    loadComponent: () =>
      import(
        './features/auth/staff-login-page/staff-login-page'
      ).then(
        (module) => module.StaffLoginPage,
      ),
  },
  {
    path: 'login',
    loadComponent: () =>
      import(
        './features/auth/login-page/login-page'
      ).then(
        (module) => module.LoginPage,
      ),
  },
  {
    path: 'admin',
    canMatch: [
      roleGuard(
        ['ADMIN'],
        '/login',
      ),
    ],
    loadComponent: () =>
      loadRemoteModule(
        'admin',
        './Component',
      ).then(
        (remoteModule) => remoteModule.App,
      ),
  },
  {
    path: 'teacher/students/:studentId',
    canMatch: [
      roleGuard(
        ['DOCENTE'],
        '/login',
      ),
    ],
    loadComponent: () =>
      loadRemoteModule(
        'teacher',
        './Component',
      ).then(
        (remoteModule) => remoteModule.App,
      ),
  },
  {
    path: 'teacher',
    canMatch: [
      roleGuard(
        ['DOCENTE'],
        '/login',
      ),
    ],
    loadComponent: () =>
      loadRemoteModule(
        'teacher',
        './Component',
      ).then(
        (remoteModule) => remoteModule.App,
      ),
  },
  {
    path: 'student/activity/:activityId',
    canMatch: [
      roleGuard(
        ['ESTUDIANTE'],
        '/login',
      ),
    ],
    loadComponent: () =>
      loadRemoteModule(
        'student',
        './ActivityComponent',
      ).then(
        (remoteModule) =>
          remoteModule.ActivityPage,
      ),
  },
  {
    path: 'student',
    canMatch: [
      roleGuard(
        ['ESTUDIANTE'],
        '/login',
      ),
    ],
    loadComponent: () =>
      loadRemoteModule(
        'student',
        './Component',
      ).then(
        (remoteModule) => remoteModule.App,
      ),
  },
  {
    path: '',
    pathMatch: 'full',
    redirectTo: 'login',
  },
  {
    path: '**',
    redirectTo: 'login',
  },
];