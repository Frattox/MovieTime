import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FilmAcquistabileComponent } from './film-acquistabile.component';

describe('FilmAcquistabileComponent', () => {
  let component: FilmAcquistabileComponent;
  let fixture: ComponentFixture<FilmAcquistabileComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FilmAcquistabileComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(FilmAcquistabileComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
