import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InfoOrdineComponent } from './info-ordine.component';

describe('InfoOrdineComponent', () => {
  let component: InfoOrdineComponent;
  let fixture: ComponentFixture<InfoOrdineComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [InfoOrdineComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(InfoOrdineComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
