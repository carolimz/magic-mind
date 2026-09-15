import { inject } from '@angular/core';
import type { HttpInterceptorFn } from '@angular/common/http';

import { AuthSessionService } from '../session/auth-session.service';

export const authTokenInterceptor: HttpInterceptorFn = (
  request,
  next,
) => {
  const sessionService = inject(AuthSessionService);
  const token = sessionService.getToken();

  const isAuthenticationRequest =
    request.url.includes('/auth/login') ||
    request.url.includes('/auth/estudiante/login');

  if (!token || isAuthenticationRequest) {
    return next(request);
  }

  const authenticatedRequest = request.clone({
    setHeaders: {
      Authorization: `Bearer ${token}`,
    },
  });

  return next(authenticatedRequest);
};