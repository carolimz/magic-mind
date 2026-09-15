import type { UserRole } from './auth.models';

export interface UserRequest {
  nombre: string;
  apellido: string;
  correo: string;
  password: string;
  rol: UserRole;
}

export interface UserResponse {
  id: number;
  nombre: string;
  apellido: string;
  correo: string;
  rol: UserRole;
  activo: boolean;
}