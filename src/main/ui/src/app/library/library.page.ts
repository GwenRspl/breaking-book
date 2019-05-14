import {Component, OnInit, ViewChild} from '@angular/core';
import {Book} from './book.model';
import {ActionSheetController, IonSlides} from '@ionic/angular';
import {BooksService} from './services/books.service';
import {TokenStorageService} from '../authentication/services/token-storage.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-library',
  templateUrl: './library.page.html',
  styleUrls: ['./library.page.scss'],
})
export class LibraryPage implements OnInit {
  @ViewChild('slideWithButtons') slideWithNav: IonSlides;
  slideOpts = {
    initialSlide: 1,
    slidesPerView: 4,
    speed: 400
  };
  private userInput: string = '';

  constructor(private booksService: BooksService,
              private tokenStorageService: TokenStorageService,
              private router: Router,
              private actionSheetCtrl: ActionSheetController) {
  }

  private _userId: number;

  get userId(): number {
    return this._userId;
  }

  private _books: Book[] = [];

  get books(): Book[] {
    return this._books;
  }

  private _currentlyReading: Book[] = [];

  get currentlyReading(): Book[] {
    return this._currentlyReading;
  }

  private _genreList: string[] = [];

  get genreList(): string[] {
    return this._genreList;
  }

  private _currentSelection: Book[] = [];

  get currentSelection(): Book[] {
    return this._currentSelection;
  }

  private _sortOptions: string[] = ['Author', 'Title', 'Rating'];

  get sortOptions(): string[] {
    return this._sortOptions;
  }

  ngOnInit() {
    this.retrieveUserId();
    this.retrieveBooks();
    this.retrieveGenreList();
  }

  retrieveUserId() {
    this._userId = +this.tokenStorageService.getUserId();
  }


  retrieveBooks() {
    this.booksService.getBooks(this.userId).subscribe(
      data => {
        this._books = data;
        this._currentSelection = this.books;
        this.retrieveCurrentlyReading();

      },
      error => {
        console.log(error);
      }
    );
  }

  retrieveGenreList() {
    this._genreList = ['Fantasy', 'SF', 'Fiction', 'Non-fiction', 'Short stories'];
  }

  retrieveCurrentlyReading() {
    this._currentlyReading = this.books.filter(book => book.status === 'ONGOING');
  }

  nextSlide(slideView) {
    slideView.slideNext(500);
  }

  previousSlide(slideView) {
    slideView.slidePrev(500);
  }


  filter() {
    this._currentSelection = this.books.filter(book => {
      return book.title.toLowerCase().indexOf(this.userInput.toLowerCase()) > -1 ||
        book.authors.toString().toLowerCase().indexOf(this.userInput.toLowerCase()) > -1;
    })
  }

  addNewBook() {
    this.actionSheetCtrl.create({
      mode: 'ios',
      header: 'How do you want to add a book ?',
      buttons: [
        {
          text: 'Search on GoogleBooks',
          handler: () => {
            this.router.navigateByUrl('/library/new');
          }
        },
        {
          text: 'Fill out the form myself',
          handler: () => {
            this.router.navigateByUrl('/library/new');
          }
        },
        {
          text: 'Cancel',
          role: 'cancel'
        }
      ]
    }).then(actionSheetEl => {
      actionSheetEl.present();
    });

  }
}
