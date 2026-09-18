import { HttpErrorResponse, HttpInterceptorFn } from '@angular/common/http';
import { throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';

export const serverErrorInterceptor: HttpInterceptorFn = (req, next) => {
  return next(req).pipe(
    catchError((error: unknown) => {
      if (error instanceof HttpErrorResponse) {
        // Status 0 significa que no hubo respuesta del servidor (caído, CORS, sin internet)
        if (error.status === 0) {
          const friendlyError = new HttpErrorResponse({
            error: { message: 'No pudimos conectar con el servidor. Verifica tu conexión o intenta más tarde.' },
            headers: error.headers,
            status: 0,
            statusText: 'Unknown Error',
            url: error.url || undefined,
          });
          return throwError(() => friendlyError);
        }
        
        // Status 5xx significa error interno del servidor
        if (error.status >= 500) {
          const friendlyError = new HttpErrorResponse({
            error: { message: 'El servidor está experimentando problemas temporales. Por favor, intenta más tarde.' },
            headers: error.headers,
            status: error.status,
            statusText: error.statusText,
            url: error.url || undefined,
          });
          return throwError(() => friendlyError);
        }
      }
      
      return throwError(() => error);
    })
  );
};
