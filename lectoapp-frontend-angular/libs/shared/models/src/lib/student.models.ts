export interface CreateStudentRequest {
  nombre: string;
  apellido: string;
  fechaNacimiento: string;
}

export type UpdateStudentRequest = CreateStudentRequest;

export interface StudentResponse {
  id: number;
  nombre: string;
  apellido: string;
  codigoAcceso: string;
  fechaNacimiento: string;
  activo: boolean;
  ultimaSesion: string | null;
  docenteId: number;
  createdAt: string;
  updatedAt: string;
}

export interface ValidateStudentCodeRequest {
  codigoAcceso: string;
}

export interface StudentCodeValidationResponse {
  valido: boolean;
  estudianteId: number | null;
  nombre: string | null;
  apellido: string | null;
}

export interface ResumenEstadisticoEstudianteResponse {
  estudianteId: number;
  nombreCompleto: string;
  etapaId: number;
  nombreEtapa: string;
  actividadesRealizadas: number;
  promedioGeneral: number;
  porcentajeExito: number;
  tiempoPromedioSegundos: number;
  ultimaActividad: string | null;
}