import type { UserRole } from './auth.models';

export interface CreateUserRequest {
  nombre: string;
  apellido: string;
  correo: string;
  password: string;
  rol: UserRole;
}

export type UpdateUserRequest = Omit<CreateUserRequest, 'password'>;

export interface UserResponse {
  id: number;
  nombre: string;
  apellido: string;
  correo: string;
  rol: UserRole;
  activo: boolean;
}