import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {RouteReuseStrategy} from '@angular/router';

import {IonicModule, IonicRouteStrategy} from '@ionic/angular';
import {SplashScreen} from '@ionic-native/splash-screen/ngx';
import {StatusBar} from '@ionic-native/status-bar/ngx';

import {AppComponent} from './app.component';
import {AppRoutingModule} from './app-routing.module';
import {HeaderComponent} from './header/header.component';
import {FooterComponent} from './footer/footer.component';
import {SettingsComponent} from './header/settings/settings.component';
import {HeaderService} from './header/services/header.service';
import {SignInPageModule} from './authentication/sign-in/sign-in.module';
import {LibraryPageModule} from './library/library.module';
import {FriendsPageModule} from './friends/friends.module';
import {ChooseBookModalComponent} from './shared/modals/choose-book-modal/choose-book-modal.component';


@NgModule({
  declarations: [AppComponent,
    HeaderComponent,
    FooterComponent,
    SettingsComponent,
    ChooseBookModalComponent
  ],
  entryComponents: [
    SettingsComponent,
    ChooseBookModalComponent
  ],
  imports: [BrowserModule,
    IonicModule.forRoot(),
    AppRoutingModule,
    SignInPageModule.forRoot(),
    LibraryPageModule.forRoot(),
    FriendsPageModule.forRoot()
  ],
  providers: [
    StatusBar,
    SplashScreen,
    {provide: RouteReuseStrategy, useClass: IonicRouteStrategy},
    HeaderService
  ],
  exports: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}
