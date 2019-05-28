import { CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ShowFriendPage } from './show-friend.page';

describe('ShowFriendPage', () => {
  let component: ShowFriendPage;
  let fixture: ComponentFixture<ShowFriendPage>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ShowFriendPage ],
      schemas: [CUSTOM_ELEMENTS_SCHEMA],
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ShowFriendPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
