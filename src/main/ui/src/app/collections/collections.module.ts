import {ModuleWithProviders, NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {FormsModule} from '@angular/forms';
import {RouterModule, Routes} from '@angular/router';

import {IonicModule} from '@ionic/angular';

import {CollectionsPage} from './collections.page';
import {CollectionsService} from './services/collections.service';
import {SharedModule} from '../shared/shared.module';

const routes: Routes = [
  {
    path: '',
    component: CollectionsPage
  }
];

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    RouterModule.forChild(routes),
    SharedModule
  ],
  declarations: [
    CollectionsPage
  ]
})
export class CollectionsPageModule {
  static forRoot(): ModuleWithProviders {
    return {
      ngModule: CollectionsPageModule,
      providers: [
        CollectionsService
      ]
    }
  }
}
