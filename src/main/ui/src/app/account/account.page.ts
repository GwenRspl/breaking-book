import {Component, OnInit} from '@angular/core';
import {User} from '../authentication/user.model';
import {ModalController} from '@ionic/angular';
import {EditAccountComponent} from './edit-account/edit-account.component';
import {AccountService} from './services/account.service';
import {TokenStorageService} from '../authentication/services/token-storage.service';

@Component({
  selector: 'app-account',
  templateUrl: './account.page.html',
  styleUrls: ['./account.page.scss'],
})
export class AccountPage implements OnInit {
  private _user: User;
  private _defaultAvatar = '../../assets/default_avatar.png';
  private _finishedLoading = false;

  constructor(private modalCtrl: ModalController,
              private accountService: AccountService,
              private tokenStorageService: TokenStorageService) {
  }

  get user(): User {
    return this._user;
  }

  get defaultAvatar(): string {
    return this._defaultAvatar;
  }

  get finishedLoading(): boolean {
    return this._finishedLoading;
  }

  ngOnInit() {
    this.retrieveUser()
  }

  retrieveUser() {
    this.accountService.getUser(+this.tokenStorageService.getUserId()).subscribe(
      data => {
        this._user = data;
        this._finishedLoading = true;
      },
      error => {
        console.log(error);
      }
    )
  }

  editAccount() {
    this.modalCtrl.create({
      component: EditAccountComponent,
      componentProps: {user: this.user},
      cssClass: 'edit-account-modal'
    }).then(
      modal => {
        modal.present();
        return modal.onDidDismiss();
      })
      .then(resultData => {
        if (resultData.role === 'success') {
          this.retrieveUser();
        }
      })

  }

}
