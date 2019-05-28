import {Component, Input, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {AccountService} from '../services/account.service';
import {EditUserInfo} from '../edit-user-info';
import {ModalController} from '@ionic/angular';
import {User} from '../../authentication/user.model';

@Component({
  selector: 'app-edit-account',
  templateUrl: './edit-account.component.html',
  styleUrls: ['./edit-account.component.scss'],
})
export class EditAccountComponent implements OnInit {
  @Input() user: User;
  editForm: FormGroup;
  submitted: boolean = false;
  private _userId: number;

  constructor(private formBuilder: FormBuilder,
              private accountService: AccountService,
              private modalCtrl: ModalController) {
  }

  get f() {
    return this.editForm.controls;
  }


  get userId(): number {
    return this._userId;
  }

  ngOnInit() {
    this.initEditForm();
    this.retrieveUserId();
  }


  retrieveUserId() {
    this._userId = this.user.id;
  }

  initEditForm() {
    this.editForm = this.formBuilder.group({
      email: [this.user.email, [Validators.required, Validators.email]],
      avatar: [this.user.avatar]
    })
  }

  editAccount() {
    this.submitted = true;
    if (this.editForm.invalid) {
      console.log('invalid form');
      return;
    }
    let editUserInfo: EditUserInfo = new EditUserInfo(
      this.editForm.value.email,
      null,
      this.editForm.value.avatar);
    this.accountService.editAccount(editUserInfo, this.userId).subscribe(
      data => {
        this.modalCtrl.dismiss(null, 'success');
      },
      error => {
        console.log(error);
      }
    )
  }

  cancel() {
    this.modalCtrl.dismiss();
  }
}


