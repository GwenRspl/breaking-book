import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {IonicModule} from '@ionic/angular';

import {DisplayBookListComponent} from './display-book-list/display-book-list.component';

@NgModule({
  imports: [CommonModule, IonicModule],
  declarations: [DisplayBookListComponent],
  exports: [DisplayBookListComponent]
})
export class SharedModule {
}
