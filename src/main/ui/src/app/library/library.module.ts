import {ModuleWithProviders, NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {RouterModule, Routes} from '@angular/router';
import {HttpClientModule} from '@angular/common/http';
import {IonicModule} from '@ionic/angular';

import {LibraryPage} from './library.page';
import {BooksService} from './services/books.service';
import {BookResolver} from './services/book-resolver.service';


const routes: Routes = [
  {
    path: '',
    component: LibraryPage
    // children: [
    //   {
    //     path: 'new',
    //     loadChildren: './new-book/new-book.module#NewBookPageModule'
    //   },
    //   {
    //     path: 'show',
    //     loadChildren: './show-book/show-book.module#ShowBookPageModule'
    //   }
    // ]
  }
];

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    RouterModule.forChild(routes),
    HttpClientModule,
    ReactiveFormsModule
  ],
  declarations: [
    LibraryPage
  ],
  entryComponents: []
})
export class LibraryPageModule {
  static forRoot(): ModuleWithProviders {
    return {
      ngModule: LibraryPageModule,
      providers: [
        BooksService,
        BookResolver
      ]
    }
  }
}
