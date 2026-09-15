import { computed, inject, Injectable, signal } from '@angular/core';
import type {
  LoginResponse,
  UserRole,
} from '@lectoapp-frontend-angular/models';

import { AUTH_STORAGE } from '../config/auth-storage.token';

const AUTH_SESSION_KEY = 'lectoapp.auth-session';

@Injectable({
  providedIn: 'root',
})
export class AuthSessionService {
  private readonly storage = inject(AUTH_STORAGE);

  private readonly sessionState = signal<LoginResponse | null>(
    this.readStoredSession(),
  );

  readonly session = this.sessionState.asReadonly();

  readonly isAuthenticated = computed(
    () => this.sessionState() !== null,
  );

  readonly role = computed(
    () => this.sessionState()?.rol ?? null,
  );

  readonly fullName = computed(() => {
    const session = this.sessionState();

    if (!session) {
      return null;
    }

    return `${session.nombre} ${session.apellido}`.trim();
  });

  saveSession(session: LoginResponse): void {
    this.storage.setItem(
      AUTH_SESSION_KEY,
      JSON.stringify(session),
    );

    this.sessionState.set(session);
  }

  clearSession(): void {
    this.storage.removeItem(AUTH_SESSION_KEY);
    this.sessionState.set(null);
  }

  getToken(): string | null {
    return this.sessionState()?.token ?? null;
  }

  hasRole(...roles: UserRole[]): boolean {
    const currentRole = this.role();

    return currentRole !== null && roles.includes(currentRole);
  }

  private readStoredSession(): LoginResponse | null {
    const storedSession = this.storage.getItem(AUTH_SESSION_KEY);

    if (!storedSession) {
      return null;
    }

    try {
      return JSON.parse(storedSession) as LoginResponse;
    } catch {
      this.storage.removeItem(AUTH_SESSION_KEY);
      return null;
    }
  }
}