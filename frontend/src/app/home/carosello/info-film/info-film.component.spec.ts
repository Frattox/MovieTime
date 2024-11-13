import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InfoFilmComponent } from './info-film.component';

describe('InfoFilmComponent', () => {
  let component: InfoFilmComponent;
  let fixture: ComponentFixture<InfoFilmComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [InfoFilmComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(InfoFilmComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
