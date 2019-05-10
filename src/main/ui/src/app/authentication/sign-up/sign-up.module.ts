import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import { Routes, RouterModule } from '@angular/router';

import { IonicModule } from '@ionic/angular';

import { SignUpPage } from './sign-up.page';
import {AuthenticationService} from "../services/authentication.service";
import {HttpClientModule} from "@angular/common/http";

const routes: Routes = [
  {
    path: '',
    component: SignUpPage
  }
];

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    IonicModule,
    RouterModule.forChild(routes),
    HttpClientModule
  ],
  declarations: [SignUpPage]
})
export class SignUpPageModule {}
