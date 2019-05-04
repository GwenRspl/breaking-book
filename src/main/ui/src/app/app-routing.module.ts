import { NgModule } from '@angular/core';
import { PreloadAllModules, RouterModule, Routes } from '@angular/router';

const routes: Routes = [
  { path: '', redirectTo: 'home', pathMatch: 'full' },
  { path: 'home', loadChildren: './home/home.module#HomePageModule' },
  { path: 'library', loadChildren: './library/library.module#LibraryPageModule' },
  { path: 'collections', loadChildren: './collections/collections.module#CollectionsPageModule' },
  { path: 'wishlists', loadChildren: './wishlists/wishlists.module#WishlistsPageModule' },
  { path: 'friends', loadChildren: './friends/friends.module#FriendsPageModule' },
  { path: 'account', loadChildren: './account/account.module#AccountPageModule' },
  { path: 'contact', loadChildren: './contact/contact.module#ContactPageModule' },
  { path: 'sign-up', loadChildren: './authentication/sign-up/sign-up.module#SignUpPageModule' },
  { path: 'sign-in', loadChildren: './authentication/sign-in/sign-in.module#SignInPageModule' }
];

@NgModule({
  imports: [
    RouterModule.forRoot(routes, { preloadingStrategy: PreloadAllModules })
  ],
  exports: [RouterModule]
})
export class AppRoutingModule { }
