import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SearchViaApiPage } from './search-via-api.page';

describe('SearchViaApiPage', () => {
  let component: SearchViaApiPage;
  let fixture: ComponentFixture<SearchViaApiPage>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SearchViaApiPage ],
      schemas: [CUSTOM_ELEMENTS_SCHEMA],
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SearchViaApiPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
