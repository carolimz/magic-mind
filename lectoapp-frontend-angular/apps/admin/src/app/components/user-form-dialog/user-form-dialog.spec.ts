import {
  ComponentFixture,
  TestBed,
} from '@angular/core/testing';
import {
  beforeEach,
  describe,
  expect,
  it,
  vi,
} from 'vitest';

import {
  UserFormDialog,
} from './user-form-dialog';

describe('UserFormDialog', () => {
  let fixture:
    ComponentFixture<UserFormDialog>;

  let component:
    UserFormDialog;

  beforeEach(async () => {
    await TestBed
      .configureTestingModule({
        imports: [
          UserFormDialog,
        ],
      })
      .compileComponents();

    fixture =
      TestBed.createComponent(
        UserFormDialog,
      );

    component =
      fixture.componentInstance;

    fixture.detectChanges();
  });

  it('crea el componente', () => {
    expect(component).toBeTruthy();
  });

  it('no envía un formulario vacío', () => {
    const emitSpy = vi.spyOn(
      component.submitted,
      'emit',
    );

    component.submit();

    expect(
      component.userForm.invalid,
    ).toBe(true);

    expect(
      emitSpy,
    ).not.toHaveBeenCalled();
  });

  it('envía los datos válidos del usuario', () => {
    const emitSpy = vi.spyOn(
      component.submitted,
      'emit',
    );

    component.userForm.setValue({
      nombre: 'Laura',
      apellido: 'Gómez',
      correo: 'laura@lectoapp.com',
      password: 'Temporal123',
      rol: 'DOCENTE',
    });

    component.submit();

    expect(
      emitSpy,
    ).toHaveBeenCalledWith({
      nombre: 'Laura',
      apellido: 'Gómez',
      correo: 'laura@lectoapp.com',
      password: 'Temporal123',
      rol: 'DOCENTE',
    });
  });

  it('no cierra mientras está guardando', () => {
    fixture.componentRef.setInput(
      'saving',
      true,
    );

    fixture.detectChanges();

    const emitSpy = vi.spyOn(
      component.closed,
      'emit',
    );

    component.close();

    expect(
      emitSpy,
    ).not.toHaveBeenCalled();
  });

  it('cierra cuando no está guardando', () => {
    const emitSpy = vi.spyOn(
      component.closed,
      'emit',
    );

    component.close();

    expect(
      emitSpy,
    ).toHaveBeenCalledOnce();
  });
});