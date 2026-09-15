import { inject } from '@angular/core';
import type { CanMatchFn } from '@angular/router';
import { Router } from '@angular/router';
import type { UserRole } from '@lectoapp-frontend-angular/models';

import { AuthSessionService } from '../session/auth-session.service';

const destinationByRole: Record<UserRole, string> = {
  ADMIN: '/admin',
  DOCENTE: '/teacher',
  ESTUDIANTE: '/student',
};

export function roleGuard(
  allowedRoles: readonly UserRole[],
  loginRoute: string,
): CanMatchFn {
  return () => {
    const sessionService = inject(AuthSessionService);
    const router = inject(Router);

    if (!sessionService.isAuthenticated()) {
      return router.createUrlTree([loginRoute]);
    }

    const currentRole = sessionService.role();

    if (currentRole && allowedRoles.includes(currentRole)) {
      return true;
    }

    if (currentRole) {
      return router.createUrlTree([
        destinationByRole[currentRole],
      ]);
    }

    return router.createUrlTree([loginRoute]);
  };
}