import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TapToTopsComponent } from './tap-to-tops.component';

describe('TapToTopsComponent', () => {
  let component: TapToTopsComponent;
  let fixture: ComponentFixture<TapToTopsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ TapToTopsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(TapToTopsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
