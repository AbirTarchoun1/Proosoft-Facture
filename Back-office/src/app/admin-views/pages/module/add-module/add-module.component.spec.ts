import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddModuleComponent } from './add-module.component';

describe('AddModuleComponent', () => {
  let component: AddModuleComponent;
  let fixture: ComponentFixture<AddModuleComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AddModuleComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AddModuleComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
