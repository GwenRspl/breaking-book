import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ShowBookPage } from './show-book.page';

describe('ShowBookPage', () => {
  let component: ShowBookPage;
  let fixture: ComponentFixture<ShowBookPage>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ShowBookPage ],
      schemas: [CUSTOM_ELEMENTS_SCHEMA],
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ShowBookPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
