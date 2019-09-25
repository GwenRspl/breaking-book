import {NgModule} from '@angular/core';
import {PreloadAllModules, RouterModule, Routes} from '@angular/router';
import {BookResolver} from './library/services/book-resolver.service';
import {FriendResolver} from './friends/services/friend-resolver.service';
import {AuthGuard} from './authentication/services/auth-guard.service';

const routes: Routes = [
  {path: '', redirectTo: 'home', pathMatch: 'full'},
  {path: 'home', loadChildren: './home/home.module#HomePageModule'},
  {path: 'library', loadChildren: './library/library.module#LibraryPageModule', canActivate: [AuthGuard]},
  {
    path: 'collections',
    loadChildren: './collections/collections.module#CollectionsPageModule',
    canActivate: [AuthGuard]
  },
  {path: 'wishlists', loadChildren: './wishlists/wishlists.module#WishlistsPageModule', canActivate: [AuthGuard]},
  {path: 'friends', loadChildren: './friends/friends.module#FriendsPageModule', canActivate: [AuthGuard]},
  {
    path: 'friends/new',
    loadChildren: './friends/new-friend/new-friend.module#NewFriendPageModule',
    canActivate: [AuthGuard]
  },

  {path: 'account', loadChildren: './account/account.module#AccountPageModule', canActivate: [AuthGuard]},
  {path: 'contact', loadChildren: './contact/contact.module#ContactPageModule'},
  {path: 'sign-up', loadChildren: './authentication/sign-up/sign-up.module#SignUpPageModule'},
  {path: 'sign-in', loadChildren: './authentication/sign-in/sign-in.module#SignInPageModule'},
  {path: 'library/new', loadChildren: './library/new-book/new-book.module#NewBookPageModule', canActivate: [AuthGuard]},
  {
    path: 'library/search-via-api',
    loadChildren: './library/search-via-api/search-via-api.module#SearchViaApiPageModule',
    canActivate: [AuthGuard]
  },
  {
    path: 'friends/show/:friendId', loadChildren: './friends/show-friend/show-friend.module#ShowFriendPageModule',
    resolve: {friend: FriendResolver},
    canActivate: [AuthGuard]
  },
  {
    path: 'library/show/:bookId', loadChildren: './library/show-book/show-book.module#ShowBookPageModule',
    resolve: {book: BookResolver},
    canActivate: [AuthGuard]
  }


];

@NgModule({
  imports: [
    RouterModule.forRoot(routes, {preloadingStrategy: PreloadAllModules})
  ],
  providers: [AuthGuard],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
