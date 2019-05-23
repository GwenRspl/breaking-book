import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {RouterModule, Routes} from '@angular/router';

import {IonicModule} from '@ionic/angular';

import {ShowBookPage} from './show-book.page';

const routes: Routes = [
  {
    path: '',
    component: ShowBookPage
  }
];

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    RouterModule.forChild(routes),
    ReactiveFormsModule
  ],
  declarations: [
    ShowBookPage
  ],
  entryComponents: []
})
export class ShowBookPageModule {
}
