import {ModuleWithProviders, NgModule} from '@angular/core';
import { CommonModule } from '@angular/common';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import { Routes, RouterModule } from '@angular/router';
import {HttpClientModule} from '@angular/common/http';

import { IonicModule } from '@ionic/angular';

import { SignInPage } from './sign-in.page';
import {AuthenticationService} from '../services/authentication.service';
import {TokenStorageService} from '../services/token-storage.service';
import {HttpInterceptorProviders} from '../services/auth-interceptor';

const routes: Routes = [
  {
    path: '',
    component: SignInPage
  }
];

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    RouterModule.forChild(routes),
    ReactiveFormsModule,
    HttpClientModule
  ],
  declarations: [SignInPage]
})
export class SignInPageModule {
  static forRoot(): ModuleWithProviders {
    return {
      ngModule: SignInPageModule,
      providers: [
        AuthenticationService,
        TokenStorageService,
        HttpInterceptorProviders
      ]
    }
  }
}

