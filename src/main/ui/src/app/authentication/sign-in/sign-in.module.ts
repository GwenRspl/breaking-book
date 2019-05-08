import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import { Routes, RouterModule } from '@angular/router';

import { IonicModule } from '@ionic/angular';

import { SignInPage } from './sign-in.page';
import {AuthenticationService} from '../service/authentication.service';
import {HttpClientModule} from '@angular/common/http';
import {TokenStorageService} from '../token-storage.service';

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
  declarations: [SignInPage],
  providers: [AuthenticationService, TokenStorageService]
})
export class SignInPageModule {}
