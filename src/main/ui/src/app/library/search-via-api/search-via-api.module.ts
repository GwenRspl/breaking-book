import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {FormsModule} from '@angular/forms';
import {RouterModule, Routes} from '@angular/router';

import {IonicModule} from '@ionic/angular';

import {SearchViaApiPage} from './search-via-api.page';
import {GoogleApiBookModalComponent} from './google-api-book-modal/google-api-book-modal.component';

const routes: Routes = [
  {
    path: '',
    component: SearchViaApiPage
  }
];

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    RouterModule.forChild(routes)
  ],
  declarations: [SearchViaApiPage,
    GoogleApiBookModalComponent
  ],
  entryComponents: [GoogleApiBookModalComponent]
})
export class SearchViaApiPageModule {
}
