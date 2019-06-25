import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {SignInInfo} from '../sign-in-info';
import {Router} from '@angular/router';
import {AuthenticationService} from '../services/authentication.service';
import {ToastController} from '@ionic/angular';
import {TokenStorageService} from '../services/token-storage.service';
import {HeaderService} from '../../header/services/header.service';
import {BooksService} from '../../library/services/books.service';
import {CollectionsService} from '../../collections/services/collections.service';
import {WishlistsService} from '../../wishlists/services/wishlists.service';
import {FriendsService} from '../../friends/services/friends.service';

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
              private headerService: HeaderService,
              private booksService: BooksService,
              private collectionsService: CollectionsService,
              private wishlistsService: WishlistsService,
              private friendsService: FriendsService) {
  }

  get f() {
    return this.signInForm.controls;
  }

  ngOnInit() {
    this.initSignInForm();
  }

  initSignInForm() {
    this.signInForm = this.formBuilder.group({
      username: ['', [Validators.required]],
      password: ['', [Validators.required]]
    });
  }

  signInUser() {
    this.submitted = true;
    if (this.signInForm.invalid) {
      console.log('invalid form');
      return;
    }
    this.signInInfo = new SignInInfo(this.signInForm.value.username, this.signInForm.value.password);
    this.authService.attemptAuthentication(this.signInInfo).subscribe(
      authData => {
        this.tokenStorage.saveToken(authData.accessToken);
        this.tokenStorage.saveUsername(authData.username);
        this.tokenStorage.saveAuthorities(authData.authorities);
        this.authService.getUserByUsername(authData.username).subscribe(
          userData => {
            this.tokenStorage.saveUserId(userData.id.toString());
            this.isLoginFailed = false;
            this.headerService.refreshNavBar(userData.username);
            this.booksService.setUserId();
            this.collectionsService.setUserId();
            this.wishlistsService.setUserId();
            this.friendsService.setUserId();
            this.router.navigateByUrl('/library');
          },
          error => {
            console.log(error);
          }
        );
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

  async presentErrorToast(message: string) {
    const toast = await this.toastCtrl.create({
      message: message,
      duration: 10000,
      color: 'danger',
      showCloseButton: true
    });
    toast.present();
  }
}
