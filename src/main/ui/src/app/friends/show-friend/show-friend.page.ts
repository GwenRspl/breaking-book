import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {Friend} from '../friend.model';
import {Book} from '../../library/book.model';
import {FriendsService} from '../services/friends.service';
import {BooksService} from '../../library/services/books.service';
import {AlertController, ModalController, ToastController} from '@ionic/angular';
import {ChooseBookModalComponent} from '../../shared/modals/choose-book-modal/choose-book-modal.component';

@Component({
  selector: 'app-show-friend',
  templateUrl: './show-friend.page.html',
  styleUrls: ['./show-friend.page.scss'],
})
export class ShowFriendPage implements OnInit {
  friendAvatarInput: string = '';
  friendNameInput: string = '';
  private _friend: Friend;
  private _defaultAvatar = '../../../assets/default_avatar.png';
  private _defaultCover = '../../../assets/default_cover.png';
  private _currentlyBorrowedBooks: Book[] = [];
  private _readBooks: Book[] = [];
  private _finishedLoading: boolean = false;
  private _editMode: boolean = false;

  constructor(private route: ActivatedRoute,
              private friendsService: FriendsService,
              private booksService: BooksService,
              private modalCtrl: ModalController,
              private toastCtrl: ToastController,
              private router: Router,
              private alertCtrl: AlertController) {
  }


  get editMode(): boolean {
    return this._editMode;
  }

  get defaultCover(): string {
    return this._defaultCover;
  }

  get finishedLoading(): boolean {
    return this._finishedLoading;
  }

  get currentlyBorrowedBooks(): Book[] {
    return this._currentlyBorrowedBooks;
  }

  get readBooks(): Book[] {
    return this._readBooks;
  }

  get friend(): Friend {
    return this._friend;
  }

  get defaultAvatar(): string {
    return this._defaultAvatar;
  }

  ngOnInit() {
    this.retrieveFriend();
  }

  retrieveFriend() {
    this.route.data.subscribe(
      data => {
        this._friend = data['friend'];
        this.friendNameInput = this.friend.name;
        this.friendAvatarInput = this.friend.avatar;
        this.retrieveCurrentlyBorrowedBooks();
        if (this.friend.historyBookIds != null && this.friend.historyBookIds.length > 0) {
          this.retrieveReadBooks();
        }
        this._finishedLoading = true;
      },
      error => console.log(error)
    );
  }

  retrieveCurrentlyBorrowedBooks() {
    this.friendsService.getAllLentBooks().subscribe(
      data => this._currentlyBorrowedBooks = data.filter(book => book.friendId == this.friend.id),
      error => console.log(error)
    );
  }

  retrieveReadBooks() {
    this.booksService.getBooks().subscribe(
      data => this._readBooks = data.filter(book => this.friend.historyBookIds.includes(book.id)),
      error => console.log(error)
    );
  }

  lendBook(bookId: number) {
    this.friendsService.lendBookToFriend(this.friend.id, bookId).subscribe(
      data => this.retrieveCurrentlyBorrowedBooks(),
      error => {
        console.log(error);
        this.presentErrorToast('Error lending book to friend. Please try again later.');
      }
    );
  }

  openLendBookModal() {
    this.modalCtrl
      .create({
        component: ChooseBookModalComponent,
        componentProps: {modalMode: 'lendBook'}
      })
      .then(modal => {
          modal.present();
          return modal.onDidDismiss();
        }
      )
      .then(modal => {
        if (modal.role == 'book') {
          this.lendBook(modal.data);
        }
      })
  }

  presentErrorToast(message: string) {
    this.toastCtrl.create({
      message: message,
      color: 'danger',
      duration: 2000
    }).then(toast => toast.present());
  }

  getBackBook(bookId: number) {
    this.friendsService.getBackBookFromFriend(bookId).subscribe(
      data => this.retrieveCurrentlyBorrowedBooks(),
      error => console.log(error)
    );
  }

  deleteFriend() {
    this.friendsService.deleteFriend(this.friend.id).subscribe(
      data => this.router.navigateByUrl('/friends'),
      error => console.log(error)
    );
  }

  presentDeleteFriendAlert() {
    this.alertCtrl.create({
      message: 'Are you sure you want to delete this friend?',
      cssClass: 'alert-btn',
      buttons: [
        {
          text: 'Yes',
          role: 'danger',
          cssClass: 'delete-btn',
          handler: () => {
            this.deleteFriend();
          }
        },
        {
          text: 'No',
          role: 'cancel',
          cssClass: 'cancel-btn',
          handler: () => {
            this.alertCtrl.dismiss();
          }
        }
      ]
    }).then(alert => alert.present())

  }

  toggleEditMode() {
    this._editMode = !this.editMode;
  }

  saveChanges() {
    if (this.friendNameInput == '') {
      this.presentErrorToast('The name of a friend cannot be empty.');
      return;
    }
    this._friend.name = this.friendNameInput;
    this._friend.avatar = this.friendAvatarInput;
    this.friendsService.updateFriend(this.friend).subscribe(
      data => this.toggleEditMode(),
      error => console.log(error)
    );

  }
}
