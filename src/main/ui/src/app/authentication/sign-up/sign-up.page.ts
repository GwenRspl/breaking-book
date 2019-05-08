import { Component, OnInit } from '@angular/core';
import {AbstractControl, FormBuilder, FormGroup, Validators} from "@angular/forms";
import {AuthenticationService} from "../service/authentication.service";
import {SignUpInfo} from "../sign-up-info";
import {ToastController} from "@ionic/angular";
import {Router} from "@angular/router";

@Component({
  selector: 'app-sign-up',
  templateUrl: './sign-up.page.html',
  styleUrls: ['./sign-up.page.scss'],
})
export class SignUpPage implements OnInit {
  registerForm: FormGroup;
  submitted: boolean = false;

  constructor(private formBuilder: FormBuilder,
              private authService: AuthenticationService,
              private toastCtrl: ToastController,
              private router: Router) { }

  ngOnInit() {
    this.initRegisterForm();
  }

  initRegisterForm() {
    this.registerForm = this.formBuilder.group({
      username: ['', [Validators.required, Validators.minLength(3)]],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]],
      confirmPassword: ['', [Validators.required]]
    },
      {
        validator: SignUpPage.passwordsNotMatching
      })

  }

  get f() {
    return this.registerForm.controls;
  }

  registerUser() {
    this.submitted = true;
    if (this.registerForm.invalid) {
      console.log('invalid form');
      return;
    }
    let signUpInfo: SignUpInfo = new SignUpInfo(
        this.registerForm.value.username,
        this.registerForm.value.email,
        this.registerForm.value.password);
    this.authService.signUp(signUpInfo).subscribe(
        data => {
          console.log(data);
          this.presentSuccessToast('You are successfully registered!');
        },
        error => {
          console.log(error);
          this.presentErrorToast(JSON.stringify(error.error.message));

        }
    )
  }
  async presentSuccessToast(message:string) {
    const toast = await this.toastCtrl.create({
      message: message,
      duration: 10000,
      buttons: [
        {
          side: 'end',
          icon: 'arrow-forward',
          text: 'Sign In!',
          handler: () => {
            this.goToSignIn();
          }
        }
      ]
    });
    toast.present();
  }

  async presentErrorToast(message:string) {
    const toast = await this.toastCtrl.create({
      message: message.substr(1).slice(0,-1),
      duration: 10000,
      color: 'danger',
      showCloseButton: true
    });
    toast.present();
  }

  private goToSignIn() {
    this.router.navigateByUrl('/sign-in');
  }

  static passwordsNotMatching(abstractControl: AbstractControl) {
    let password = abstractControl.get('password').value;
    let confirmPassword = abstractControl.get('confirmPassword').value;
    if(password != confirmPassword) {
      abstractControl.get('confirmPassword').setErrors({'passwordsNotMatching' : true})
    } else {
      return null;
    }
  }

}
