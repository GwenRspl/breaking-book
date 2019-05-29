import {Component, OnInit, ViewChild} from '@angular/core';
import {IonSlides} from '@ionic/angular';
import {Book} from '../library/book.model';
import {Router} from '@angular/router';
import {Friend} from './friend.model';
import {FriendsService} from './services/friends.service';
import {TokenStorageService} from '../authentication/services/token-storage.service';

@Component({
  selector: 'app-friends',
  templateUrl: './friends.page.html',
  styleUrls: ['./friends.page.scss'],
})
export class FriendsPage implements OnInit {
  @ViewChild('slideWithButtons') slideWithNav: IonSlides;
  slideOpts = {
    initialSlide: 1,
    slidesPerView: 4,
    speed: 400
  };
  private _userId: number;
  private _booksCurrentlyLent: Book[] = [];
  private _friends: Friend[] = [];
  private _defaultCover = '../../assets/default_cover.png';
  private _defaultAvatar = '../../assets/default_avatar.png';
  private _sortOptions: string[] = ['A to Z', 'Z to A'];

  constructor(private router: Router,
              private friendsService: FriendsService,
              private tokenStorageService: TokenStorageService) {
  }


  get sortOptions(): string[] {
    return this._sortOptions;
  }

  get booksCurrentlyLent(): Book[] {
    return this._booksCurrentlyLent;
  }

  get defaultAvatar(): string {
    return this._defaultAvatar;
  }

  get friends(): Friend[] {
    return this._friends;
  }

  get defaultCover(): string {
    return this._defaultCover;
  }

  get userId(): number {
    return this._userId;
  }

  ngOnInit() {
    this.retrieveUserId();
  }

  ionViewWillEnter() {
    this.retrieveFriends();
    this.retrieveBooksCurrentlyLent();
  }

  retrieveUserId() {
    this._userId = +this.tokenStorageService.getUserId();
  }

  retrieveFriends() {
    this.friendsService.getAllFriends(this.userId).subscribe(
      data => this._friends = data,
      error => console.log(error)
    );
  }

  retrieveBooksCurrentlyLent() {
    this.friendsService.getAllLentBooks(this.userId).subscribe(
      data => this._booksCurrentlyLent = data,
      error => console.log(error)
    );
  }

  showBookDetails(bookId: number) {
    this.router.navigate((['/', 'library', 'show', bookId]));
  }

  showFriendDetails(friendId: number) {
    this.router.navigate((['/', 'friends', 'show', friendId]));
  }

  addNewFriend() {
    this.router.navigate((['/', 'friends', 'new']));
  }

  /*
  Carousel methods
   */

  nextSlide(slideView) {
    slideView.slideNext(500);
  }

  previousSlide(slideView) {
    slideView.slidePrev(500);
  }

  /**
   *  Sorting methods
   */

  sortBooks(event: any) {
    if (event.detail.value == 'A to Z') {
      this.sortAlphabeticalOrder();
    } else {
      this.sortReverseAlphabeticalOrder();
    }
  }

  sortAlphabeticalOrder() {
    this.friends.sort((a: Friend, b: Friend) => {
      if (a.name.toUpperCase() < b.name.toUpperCase()) {
        return -1;
      } else if (a.name.toUpperCase() > b.name.toUpperCase()) {
        return 1;
      } else {
        return 0;
      }
    });
  }

  sortReverseAlphabeticalOrder() {
    this.friends.sort((a: Friend, b: Friend) => {
      if (a.name.toUpperCase() < b.name.toUpperCase()) {
        return 1;
      } else if (a.name.toUpperCase() > b.name.toUpperCase()) {
        return -1;
      } else {
        return 0;
      }
    });
  }

}
