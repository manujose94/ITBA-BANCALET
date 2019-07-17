import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FirstTabPage } from './first-tab.page';

describe('FirstTabPage', () => {
  let component: FirstTabPage;
  let fixture: ComponentFixture<FirstTabPage>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FirstTabPage ],
      schemas: [CUSTOM_ELEMENTS_SCHEMA],
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FirstTabPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
