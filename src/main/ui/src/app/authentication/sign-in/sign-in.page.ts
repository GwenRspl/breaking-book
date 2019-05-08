import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {SignInInfo} from '../sign-in-info';
import {Router} from '@angular/router';
import {AuthenticationService} from '../service/authentication.service';
import {ToastController} from '@ionic/angular';
import {TokenStorageService} from '../token-storage.service';
import {HeaderService} from '../../header/header.service';

@Component({
  selector: 'app-sign-in',
  templateUrl: './sign-in.page.html',
  styleUrls: ['./sign-in.page.scss'],
})
export class SignInPage implements OnInit {
  signInForm: FormGroup;
  submitted: boolean;
  isLoginFailed = false;
  private signInInfo: SignInInfo;

  constructor(private formBuilder: FormBuilder,
              private router: Router,
              private authService: AuthenticationService,
              private toastCtrl: ToastController,
              private tokenStorage: TokenStorageService,
              private headerService: HeaderService) { }

  ngOnInit() {
    this.initSignInForm();
  }

  initSignInForm(){
    this.signInForm = this.formBuilder.group({
      username: ['', [Validators.required]],
      password: ['', [Validators.required]]
    });
  }

  get f() {
    return this.signInForm.controls;
  }

  signInUser() {
    this.submitted = true;
    if(this.signInForm.invalid) {
      console.log('invalid form');
      return;
    }
    this.signInInfo = new SignInInfo(this.signInForm.value.username, this.signInForm.value.password);
    this.authService.attemptAuthentication(this.signInInfo).subscribe(
      data => {
        this.tokenStorage.saveToken(data.token);
        this.tokenStorage.saveUsername(data.username);
        this.tokenStorage.saveAuthorities(data.authorities);
        this.isLoginFailed = false;
        this.headerService.refreshNavBar(data.username);
        this.router.navigateByUrl('/library');
      },
      error => {
        console.log(error);
        this.isLoginFailed = true;
        this.presentErrorToast('Invalid username or password.');
      }
    )

  }

  goToSignUp() {
    this.router.navigateByUrl('/sign-up');
  }

  async presentErrorToast(message:string) {
    const toast = await this.toastCtrl.create({
      message: message,
      duration: 10000,
      color: 'danger',
      showCloseButton: true
    });
    toast.present();
  }
}
