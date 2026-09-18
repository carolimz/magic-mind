import {
  ChangeDetectionStrategy,
  Component,
  computed,
  input,
  output,
} from '@angular/core';
import type {
  UserResponse,
} from '@lectoapp-frontend-angular/models';

@Component({
  selector: 'app-user-card',
  templateUrl: './user-card.html',
  styleUrl: './user-card.scss',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class UserCard {
  readonly user =
    input.required<UserResponse>();

  readonly deleting = input(false);

  readonly edit = output<void>();
  readonly delete = output<void>();

  readonly initials = computed(() => {
    const currentUser = this.user();

    return (
      currentUser.nombre.charAt(0) +
      currentUser.apellido.charAt(0)
    ).toUpperCase();
  });

  readonly roleLabel = computed(() =>
    this.user().rol === 'ADMIN'
      ? 'Administrador'
      : 'Docente',
  );

  readonly roleIcon = computed(() =>
    this.user().rol === 'ADMIN'
      ? '🛡️'
      : '👩‍🏫',
  );
}