import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { LecturesLabsComponent } from './lectures-labs.component';

describe('LecturesLabsComponent', () => {
  let component: LecturesLabsComponent;
  let fixture: ComponentFixture<LecturesLabsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ LecturesLabsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LecturesLabsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
