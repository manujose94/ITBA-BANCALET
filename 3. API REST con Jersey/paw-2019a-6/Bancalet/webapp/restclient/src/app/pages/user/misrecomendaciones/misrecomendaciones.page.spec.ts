import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MisrecomendacionesPage } from './misrecomendaciones.page';

describe('MisrecomendacionesPage', () => {
  let component: MisrecomendacionesPage;
  let fixture: ComponentFixture<MisrecomendacionesPage>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MisrecomendacionesPage ],
      schemas: [CUSTOM_ELEMENTS_SCHEMA],
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MisrecomendacionesPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
