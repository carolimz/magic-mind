import {
  AfterViewInit,
  Directive,
  ElementRef,
  inject,
  Input,
  NgZone,
  OnDestroy,
} from '@angular/core';

@Directive({
  selector: '[appFloatingBubble]',
})
export class FloatingBubbleDirective
  implements AfterViewInit, OnDestroy
{
  private readonly element =
    inject<ElementRef<HTMLElement>>(ElementRef);

  private readonly zone = inject(NgZone);

  @Input({ required: true })
  bubbleIndex = 0;

  private animationFrame: number | null = null;
  private previousTime = 0;

  private positionX = 0;
  private positionY = 0;

  private velocityX = 0;
  private velocityY = 0;

  ngAfterViewInit(): void {
    this.zone.runOutsideAngular(() => {
      this.animationFrame = requestAnimationFrame(
        () => this.initialize(),
      );
    });
  }

  ngOnDestroy(): void {
    if (this.animationFrame !== null) {
      cancelAnimationFrame(this.animationFrame);
    }
  }

  private initialize(): void {
    const host = this.element.nativeElement;
    const container = host.parentElement;

    if (!container) {
      return;
    }

    const maximumX = Math.max(
      0,
      container.clientWidth - host.offsetWidth,
    );

    const maximumY = Math.max(
      0,
      container.clientHeight - host.offsetHeight,
    );

    const initialPositions = [
      { x: 0.05, y: 0.08 },
      { x: 0.68, y: 0.12 },
      { x: 0.18, y: 0.65 },
      { x: 0.72, y: 0.68 },
      { x: 0.40, y: 0.35 },
      { x: 0.85, y: 0.40 },
    ];

    const initialPosition =
      initialPositions[
        this.bubbleIndex % initialPositions.length
      ];

    this.positionX =
      maximumX * initialPosition.x;

    this.positionY =
      maximumY * initialPosition.y;

    const speed =
      48 + this.bubbleIndex * 7;

    const directions = [
      { x: 1, y: 0.72 },
      { x: -0.78, y: 1 },
      { x: 0.82, y: -1 },
      { x: -1, y: -0.68 },
      { x: 0.5, y: -0.8 },
      { x: -0.6, y: 0.9 },
    ];

    const direction =
      directions[
        this.bubbleIndex % directions.length
      ];

    this.velocityX = speed * direction.x;
    this.velocityY = speed * direction.y;

    this.applyPosition();

    const prefersReducedMotion =
      window.matchMedia(
        '(prefers-reduced-motion: reduce)',
      ).matches;

    if (!prefersReducedMotion) {
      this.animationFrame =
        requestAnimationFrame(
          (time) => this.animate(time),
        );
    }
  }

  private animate(currentTime: number): void {
    const host = this.element.nativeElement;
    const container = host.parentElement;

    if (!container) {
      return;
    }

    if (this.previousTime === 0) {
      this.previousTime = currentTime;
    }

    const elapsedSeconds = Math.min(
      (currentTime - this.previousTime) / 1000,
      0.04,
    );

    this.previousTime = currentTime;

    const maximumX = Math.max(
      0,
      container.clientWidth - host.offsetWidth,
    );

    const maximumY = Math.max(
      0,
      container.clientHeight - host.offsetHeight,
    );

    this.positionX +=
      this.velocityX * elapsedSeconds;

    this.positionY +=
      this.velocityY * elapsedSeconds;

    if (this.positionX <= 0) {
      this.positionX = 0;
      this.velocityX =
        Math.abs(this.velocityX);
    } else if (this.positionX >= maximumX) {
      this.positionX = maximumX;
      this.velocityX =
        -Math.abs(this.velocityX);
    }

    if (this.positionY <= 0) {
      this.positionY = 0;
      this.velocityY =
        Math.abs(this.velocityY);
    } else if (this.positionY >= maximumY) {
      this.positionY = maximumY;
      this.velocityY =
        -Math.abs(this.velocityY);
    }

    this.applyPosition();

    this.animationFrame =
      requestAnimationFrame(
        (time) => this.animate(time),
      );
  }

  private applyPosition(): void {
    this.element.nativeElement.style.transform =
      `translate3d(${this.positionX}px, ${this.positionY}px, 0)`;
  }
}