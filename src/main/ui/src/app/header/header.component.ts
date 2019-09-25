import {Component, OnInit} from '@angular/core';
import {PopoverController} from "@ionic/angular";
import {SettingsComponent} from './settings/settings.component';
import {Router} from '@angular/router';
import {TokenStorageService} from '../authentication/services/token-storage.service';
import {HeaderService} from './services/header.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss'],
})
export class HeaderComponent implements OnInit {
  constructor(private popoverCtrl: PopoverController,
              private router: Router,
              private tokenStorage: TokenStorageService,
              private headerService: HeaderService) {
  }

  private _username: string;

  get username(): string {
    return this._username;
  }

  ngOnInit() {
    this.checkIfUserIsSignedIn();
    this.headerService.updateNavBar.subscribe(data => this._username = data);
  }

  onCogClick(ev: any) {
    this.popoverCtrl
      .create({
        component: SettingsComponent,
        event: ev
      })
      .then(popoverEl => {
        popoverEl.present();
        return popoverEl.onDidDismiss();
      })
      .then(resultData => {
        if (resultData.role === 'sign-out') {
          this.signOut();
        }
      })
  }

  goToHome() {
    this.router.navigateByUrl('/home');
  }

  private checkIfUserIsSignedIn() {
    if (this.tokenStorage.getToken()) {
      this._username = this.tokenStorage.getUsername();
    }
  }

  private signOut() {
    this.tokenStorage.signOut();
    this._username = '';
    this.router.navigateByUrl('/home');
  }
}
