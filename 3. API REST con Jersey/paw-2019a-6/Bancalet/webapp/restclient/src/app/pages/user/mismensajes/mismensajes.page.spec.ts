import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MismensajesPage } from './mismensajes.page';

describe('MismensajesPage', () => {
  let component: MismensajesPage;
  let fixture: ComponentFixture<MismensajesPage>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MismensajesPage ],
      schemas: [CUSTOM_ELEMENTS_SCHEMA],
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MismensajesPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
