import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {FormsModule} from '@angular/forms';
import {RouterModule, Routes} from '@angular/router';

import {IonicModule} from '@ionic/angular';

import {ShowFriendPage} from './show-friend.page';
import {LendBookModalComponent} from './lend-book-modal/lend-book-modal.component';

const routes: Routes = [
  {
    path: '',
    component: ShowFriendPage
  }
];

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    RouterModule.forChild(routes)
  ],
  declarations: [
    ShowFriendPage,
    LendBookModalComponent
  ],
  entryComponents: [LendBookModalComponent]
})
export class ShowFriendPageModule {
}
