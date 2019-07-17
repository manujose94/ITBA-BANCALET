import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ArchivedIssuesPage } from './archived-issues.page';

describe('ArchivedIssuesPage', () => {
  let component: ArchivedIssuesPage;
  let fixture: ComponentFixture<ArchivedIssuesPage>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ArchivedIssuesPage ],
      schemas: [CUSTOM_ELEMENTS_SCHEMA],
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ArchivedIssuesPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
