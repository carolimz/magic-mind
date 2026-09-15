import {
  provideHttpClient,
  withInterceptors,
} from '@angular/common/http';
import {
  ApplicationConfig,
  provideBrowserGlobalErrorListeners,
  provideZoneChangeDetection,
} from '@angular/core';
import { provideRouter } from '@angular/router';
import { API_BASE_URL } from '@lectoapp-frontend-angular/api';
import { authTokenInterceptor } from '@lectoapp-frontend-angular/auth';
import Aura from '@openng/optimus-ui-themes/aura';
import { provideOptimus } from '@openng/optimus-ui/config';

import { appRoutes } from './app.routes';

export const appConfig: ApplicationConfig = {
  providers: [
    provideBrowserGlobalErrorListeners(),
    provideZoneChangeDetection({ eventCoalescing: true }),

    provideHttpClient(
      withInterceptors([authTokenInterceptor]),
    ),

    {
      provide: API_BASE_URL,
      useValue: 'http://localhost:8080',
    },

    provideRouter(appRoutes),

    provideOptimus({
      ripple: true,
      theme: {
        preset: Aura,
        options: {
          darkModeSelector: '.lectoapp-dark',
          cssLayer: {
            name: 'primeng',
            order: 'theme, base, primeng, components, utilities',
          },
        },
      },
    }),
  ],
};