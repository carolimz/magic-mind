import { InjectionToken } from '@angular/core';

export const AUTH_STORAGE = new InjectionToken<Storage>('AUTH_STORAGE', {
  providedIn: 'root',
  factory: () => sessionStorage,
});