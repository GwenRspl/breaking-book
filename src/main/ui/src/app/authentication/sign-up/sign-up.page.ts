import {Component, OnInit} from '@angular/core';
import {AbstractControl, FormBuilder, FormGroup, Validators} from "@angular/forms";
import {AuthenticationService} from "../services/authentication.service";
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
  private _signUpSuccess: boolean = false;

  constructor(private formBuilder: FormBuilder,
              private authService: AuthenticationService,
              private toastCtrl: ToastController,
              private router: Router) {
  }

  get signUpSuccess(): boolean {
    return this._signUpSuccess;
  }

  get f() {
    return this.registerForm.controls;
  }

  passwordsNotMatching(abstractControl: AbstractControl) {
    let password = abstractControl.get('password').value;
    let confirmPassword = abstractControl.get('confirmPassword').value;
    if (password != confirmPassword) {
      abstractControl.get('confirmPassword').setErrors({'passwordsNotMatching': true});
    } else {
      return null;
    }
  }

  ngOnInit() {
    this.initRegisterForm();
  }

  ionViewWillEnter() {
    this.submitted = false;
    this._signUpSuccess = false;
    this.registerForm.reset();
  }

  initRegisterForm() {
    this.registerForm = this.formBuilder.group({
        username: ['', [Validators.required, Validators.minLength(3)]],
        email: ['', [Validators.required, Validators.email]],
        password: ['', [Validators.required, Validators.minLength(6)]],
        confirmPassword: ['', [Validators.required]]
      },
      {
        validator: this.passwordsNotMatching
      })

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
        this._signUpSuccess = true;
        setTimeout(() => {
          this.goToSignIn();
        }, 3000);
      },
      error => {
        console.log(error);
        this.presentErrorToast(JSON.stringify(error.error.message));
      }
    )
  }

  async presentErrorToast(message: string) {
    const toast = await this.toastCtrl.create({
      message: message.substr(1).slice(0, -1),
      duration: 10000,
      color: 'danger',
      showCloseButton: true
    });
    toast.present();
  }

  private goToSignIn() {
    this.router.navigateByUrl('/sign-in');
  }

}
