import {ModuleWithProviders, NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {FormsModule} from '@angular/forms';
import {RouterModule, Routes} from '@angular/router';
import {IonicModule} from '@ionic/angular';

import {FriendsPage} from './friends.page';
import {FriendsService} from './services/friends.service';
import {HttpClientModule} from '@angular/common/http';
import {FriendResolver} from './services/friend-resolver.service';

const routes: Routes = [
  {
    path: '',
    component: FriendsPage
  }
];

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    RouterModule.forChild(routes),
    HttpClientModule
  ],
  declarations: [FriendsPage]
})
export class FriendsPageModule {
  static forRoot(): ModuleWithProviders {
    return {
      ngModule: FriendsPageModule,
      providers: [
        FriendsService,
        FriendResolver
      ]
    }
  }
}
