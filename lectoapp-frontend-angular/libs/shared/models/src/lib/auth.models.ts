export type UserRole = 'ADMIN' | 'DOCENTE' | 'ESTUDIANTE';

export interface UserLoginRequest {
  correo: string;
  password: string;
}

export interface StudentLoginRequest {
  codigo: string;
}

export interface LoginResponse {
  token: string;
  tipo: string;
  nombre: string;
  apellido: string;
  correo: string | null;
  rol: UserRole;
}

export interface ApiErrorResponse {
  timestamp: string;
  status: number;
  error: string;
  message: string;
  path: string;
}