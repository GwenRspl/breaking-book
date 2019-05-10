import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { RouteReuseStrategy } from '@angular/router';

import { IonicModule, IonicRouteStrategy } from '@ionic/angular';
import { SplashScreen } from '@ionic-native/splash-screen/ngx';
import { StatusBar } from '@ionic-native/status-bar/ngx';

import { AppComponent } from './app.component';
import { AppRoutingModule } from './app-routing.module';
import {HeaderComponent} from './header/header.component';
import {FooterComponent} from './footer/footer.component';
import {SettingsComponent} from './header/settings/settings.component';
import {HeaderService} from './header/services/header.service';
import {SignInPageModule} from './authentication/sign-in/sign-in.module';
import {LibraryPageModule} from './library/library.module';


@NgModule({
  declarations: [AppComponent,
    HeaderComponent,
    FooterComponent,
    SettingsComponent
  ],
  entryComponents: [SettingsComponent],
  imports: [BrowserModule,
    IonicModule.forRoot(),
    AppRoutingModule,
    SignInPageModule.forRoot(),
    LibraryPageModule.forRoot()
  ],
  providers: [
    StatusBar,
    SplashScreen,
    { provide: RouteReuseStrategy, useClass: IonicRouteStrategy },
    HeaderService
  ],
  bootstrap: [AppComponent]
})
export class AppModule {}
