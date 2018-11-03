import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PreviousExamsComponent } from './previous-exams.component';

describe('PreviousExamsComponent', () => {
  let component: PreviousExamsComponent;
  let fixture: ComponentFixture<PreviousExamsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PreviousExamsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PreviousExamsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
