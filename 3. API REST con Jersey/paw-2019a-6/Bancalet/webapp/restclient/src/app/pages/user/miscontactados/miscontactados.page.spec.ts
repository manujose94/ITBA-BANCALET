import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MiscontactadosPage } from './miscontactados.page';

describe('MiscontactadosPage', () => {
  let component: MiscontactadosPage;
  let fixture: ComponentFixture<MiscontactadosPage>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MiscontactadosPage ],
      schemas: [CUSTOM_ELEMENTS_SCHEMA],
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MiscontactadosPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
