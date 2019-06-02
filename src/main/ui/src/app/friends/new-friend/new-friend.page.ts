import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {FriendsService} from '../services/friends.service';
import {Friend} from '../friend.model';
import {TokenStorageService} from '../../authentication/services/token-storage.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-new-friend',
  templateUrl: './new-friend.page.html',
  styleUrls: ['./new-friend.page.scss'],
})
export class NewFriendPage implements OnInit {
  friendForm: FormGroup;
  submitted: boolean = false;

  constructor(private formBuilder: FormBuilder,
              private friendsService: FriendsService,
              private tokenStorageService: TokenStorageService,
              private router: Router) {
  }

  get f() {
    return this.friendForm.controls;
  }

  ngOnInit() {
    this.initForm();
  }

  initForm() {
    this.friendForm = this.formBuilder.group({
      name: ['', [Validators.required]],
      avatar: ['']
    });
  }

  saveNewFriend() {
    this.submitted = true;
    if (this.friendForm.invalid) {
      console.log('invalid form');
      return;
    }
    console.log(this.friendForm.getRawValue());
    let newFriend: Friend = new Friend(this.friendForm.value.name, this.friendForm.value.avatar, +this.tokenStorageService.getUserId());
    this.friendsService.saveFriend(newFriend).subscribe(
      data => {
        this.router.navigate((['/', 'friends', 'show', data]));
      },
      error => console.log(error)
    );

  }
}
