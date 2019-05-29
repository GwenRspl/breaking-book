import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {Friend} from '../friend.model';
import {Book} from '../../library/book.model';
import {FriendsService} from '../services/friends.service';
import {BooksService} from '../../library/services/books.service';

@Component({
  selector: 'app-show-friend',
  templateUrl: './show-friend.page.html',
  styleUrls: ['./show-friend.page.scss'],
})
export class ShowFriendPage implements OnInit {
  private _friend: Friend;
  private _defaultAvatar = '../../../assets/default_avatar.png';
  private _currentlyBorrowedBooks: Book[] = [];
  private _readBooks: Book[] = [];
  private _finishedLoading: boolean = false;

  constructor(private route: ActivatedRoute,
              private friendsService: FriendsService,
              private booksService: BooksService) {
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
        this.retrieveCurrentlyBorrowedBooks(this.friend.id);
        if (this.friend.historyBookIds != null && this.friend.historyBookIds.length > 0) {
          this.retrieveReadBooks(this.friend.historyBookIds);
        }
        this._finishedLoading = true;
      },
      error => console.log(error)
    );
  }

  retrieveCurrentlyBorrowedBooks(friendId: number) {
    this.friendsService.getAllLentBooks().subscribe(
      data => this._currentlyBorrowedBooks = data.filter(book => book.friendId == friendId),
      error => console.log(error)
    );
  }

  retrieveReadBooks(booksIds: number[]) {
    this.booksService.getBooks(2).subscribe(
      data => this._readBooks = data.filter(book => booksIds.includes(book.id)),
      error => console.log(error)
    );
  }

}
