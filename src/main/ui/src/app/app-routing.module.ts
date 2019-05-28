import {NgModule} from '@angular/core';
import {PreloadAllModules, RouterModule, Routes} from '@angular/router';
import {BookResolver} from './library/services/book-resolver.service';

const routes: Routes = [
  {path: '', redirectTo: 'home', pathMatch: 'full'},
  {path: 'home', loadChildren: './home/home.module#HomePageModule'},
  {path: 'library', loadChildren: './library/library.module#LibraryPageModule'},
  {path: 'collections', loadChildren: './collections/collections.module#CollectionsPageModule'},
  {path: 'wishlists', loadChildren: './wishlists/wishlists.module#WishlistsPageModule'},
  {path: 'friends', loadChildren: './friends/friends.module#FriendsPageModule'},
  {path: 'account', loadChildren: './account/account.module#AccountPageModule'},
  {path: 'contact', loadChildren: './contact/contact.module#ContactPageModule'},
  {path: 'sign-up', loadChildren: './authentication/sign-up/sign-up.module#SignUpPageModule'},
  {path: 'sign-in', loadChildren: './authentication/sign-in/sign-in.module#SignInPageModule'},
  {path: 'library/new', loadChildren: './library/new-book/new-book.module#NewBookPageModule'},
  {
    path: 'library/show/:bookId',
    loadChildren: './library/show-book/show-book.module#ShowBookPageModule',
    resolve: {book: BookResolver}
  },
  {
    path: 'library/search-via-api',
    loadChildren: './library/search-via-api/search-via-api.module#SearchViaApiPageModule'
  }
];

@NgModule({
  imports: [
    RouterModule.forRoot(routes, {preloadingStrategy: PreloadAllModules})
  ],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
