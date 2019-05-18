import {Component, OnInit} from '@angular/core';
import {User} from "../authentication/user.model";
import {ModalController} from "@ionic/angular";
import {EditAccountComponent} from "./edit-account/edit-account.component";
import {AccountService} from "./services/account.service";
import {TokenStorageService} from "../authentication/services/token-storage.service";

@Component({
    selector: 'app-account',
    templateUrl: './account.page.html',
    styleUrls: ['./account.page.scss'],
})
export class AccountPage implements OnInit {
    user: User;

    constructor(private modalCtrl: ModalController,
                private accountService: AccountService,
                private tokenStorageService: TokenStorageService) {
    }

    ngOnInit() {
        this.retrieveUser()
    }


    retrieveUser() {
        this.accountService.getUser(+this.tokenStorageService.getUserId()).subscribe(
            data => {
                this.user = data;
            },
            error => {
                console.log(error);
            }
        )
    }

    editAccount() {
        this.modalCtrl.create({
            component: EditAccountComponent
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
