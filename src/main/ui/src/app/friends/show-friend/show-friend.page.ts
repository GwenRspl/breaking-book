import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {Friend} from '../friend.model';
import {Book} from '../../library/book.model';
import {FriendsService} from '../services/friends.service';
import {BooksService} from '../../library/services/books.service';
import {ModalController, ToastController} from '@ionic/angular';
import {LendBookModalComponent} from './lend-book-modal/lend-book-modal.component';

@Component({
  selector: 'app-show-friend',
  templateUrl: './show-friend.page.html',
  styleUrls: ['./show-friend.page.scss'],
})
export class ShowFriendPage implements OnInit {
  private _friend: Friend;
  private _defaultAvatar = '../../../assets/default_avatar.png';
  private _defaultCover = '../../../assets/default_cover.png';
  private _currentlyBorrowedBooks: Book[] = [];
  private _readBooks: Book[] = [];
  private _finishedLoading: boolean = false;

  constructor(private route: ActivatedRoute,
              private friendsService: FriendsService,
              private booksService: BooksService,
              private modalCtrl: ModalController,
              private toastCtrl: ToastController) {
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
        component: LendBookModalComponent,
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

  getBack(bookId: number) {
    this.friendsService.getBackBookFromFriend(bookId).subscribe(
      data => this.retrieveCurrentlyBorrowedBooks(),
      error => console.log(error)
    );
  }
}
