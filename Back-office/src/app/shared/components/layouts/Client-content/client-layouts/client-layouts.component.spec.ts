import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ClientLayoutsComponent } from './client-layouts.component';

describe('ClientLayoutsComponent', () => {
  let component: ClientLayoutsComponent;
  let fixture: ComponentFixture<ClientLayoutsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ClientLayoutsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ClientLayoutsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
