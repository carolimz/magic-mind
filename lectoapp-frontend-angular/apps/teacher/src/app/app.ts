import {
  ChangeDetectionStrategy,
  Component,
  computed,
  inject,
} from '@angular/core';
import {
  ActivatedRoute,
  Router,
  RouterLink,
} from '@angular/router';
import {
  AuthSessionService,
} from '@lectoapp-frontend-angular/auth';
import { toSignal } from '@angular/core/rxjs-interop';
import { map } from 'rxjs';

import {
  StudentListPage,
} from './features/students/student-list-page/student-list-page';
import {
  StudentProgressPage,
} from './features/students/student-progress-page/student-progress-page';

@Component({
  selector: 'app-root',
  imports: [
    RouterLink,
    StudentListPage,
    StudentProgressPage,
  ],
  templateUrl: './app.html',
  styleUrl: './app.scss',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class App {
  private readonly route =
    inject(ActivatedRoute);

  private readonly router =
    inject(Router);

  private readonly sessionService =
    inject(AuthSessionService);

  readonly teacherName = computed(
    () =>
      this.sessionService.fullName() ??
      'Docente',
  );

  readonly isStudentProgress = toSignal(
    this.route.paramMap.pipe(
      map(params => {
        const studentId = Number(params.get('studentId'));
        return Number.isInteger(studentId) && studentId > 0;
      })
    ),
    { initialValue: this.hasStudentId() }
  );

  logout(): void {
    this.sessionService.clearSession();

    void this.router.navigateByUrl(
      '/login',
    );
  }

  private hasStudentId(): boolean {
    const studentId = Number(
      this.route.snapshot.paramMap.get(
        'studentId',
      ),
    );

    return (
      Number.isInteger(studentId) &&
      studentId > 0
    );
  }
}