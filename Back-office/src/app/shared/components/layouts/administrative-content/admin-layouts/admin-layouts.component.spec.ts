import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminLayoutsComponent } from './admin-layouts.component';

describe('AdminLayoutsComponent', () => {
  let component: AdminLayoutsComponent;
  let fixture: ComponentFixture<AdminLayoutsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AdminLayoutsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AdminLayoutsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
