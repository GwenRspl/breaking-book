import {Component, OnInit, ViewChild} from '@angular/core';
import {Book} from './book.model';
import {ActionSheetController, IonSlides} from '@ionic/angular';
import {BooksService} from './services/books.service';
import {TokenStorageService} from '../authentication/services/token-storage.service';
import {Router} from '@angular/router';

interface CollectionCheckbox {
  id: number;
  name: string;
  checked?: boolean;
}

interface StatusCheckbox {
  id: number;
  name: string;
  checked?: boolean;
}

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
  public userInput: string = '';
  private _userId: number;
  private _books: Book[] = [];
  private _currentlyReading: Book[] = [];
  private _collections: CollectionCheckbox[] = [];
  private _status: StatusCheckbox[] = [
    {id: 0, name: 'Unread', checked: true},
    {id: 1, name: 'Read', checked: true},
    {id: 2, name: 'Ongoing', checked: true},
  ];
  private _currentSelection: Book[] = [];
  private _sortOptions: string[] = ['Author', 'Title', 'Rating'];
  private selectedOwnership;
  private selectedRatings;

  constructor(private booksService: BooksService,
              private tokenStorageService: TokenStorageService,
              private router: Router,
              private actionSheetCtrl: ActionSheetController) {
  }

  get userId(): number {
    return this._userId;
  }

  get books(): Book[] {
    return this._books;
  }

  get currentlyReading(): Book[] {
    return this._currentlyReading;
  }

  get collections(): CollectionCheckbox[] {
    return this._collections;
  }

  get status(): StatusCheckbox[] {
    return this._status;
  }

  get currentSelection(): Book[] {
    return this._currentSelection;
  }

  get sortOptions(): string[] {
    return this._sortOptions;
  }

  ngOnInit() {
  }

  ionViewWillEnter() {
    this.retrieveUserId();
    this.retrieveBooks();
    this.retrieveCollections();
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

  retrieveCollections() {
    let strings = ['Fantasy', 'SF', 'Fiction', 'Non-fiction', 'Short stories'];
    let i: number;
    for (i = 0; i < strings.length; i++) {
      this._collections.push({id: i, name: strings[i], checked: true});
    }
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

  searchBarFiltering() {
    this._currentSelection = this.applyFilters().filter(book => {
      return book.title.toLowerCase().indexOf(this.userInput.toLowerCase()) > -1 ||
        book.authors.toString().toLowerCase().indexOf(this.userInput.toLowerCase()) > -1;
    });

  }


  addNewBook() {
    this.actionSheetCtrl.create({
      mode: 'ios',
      header: 'How do you want to add a book ?',
      buttons: [
        {
          text: 'Search on GoogleBooks',
          handler: () => {
            this.router.navigateByUrl('/library/search-via-api');
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

  showBookDetails(bookId: number) {
    this.router.navigate((['/', 'library', 'show', bookId]));
  }

  onOwnershipChanged(event: any) {
    switch (event.detail.value) {
      case 'all': {
        this.selectedOwnership = 'all';
        break;
      }
      case 'owned': {
        this.selectedOwnership = 'owned';
        break;
      }
      case 'notOwned': {
        this.selectedOwnership = 'notOwned';
        break;
      }

    }
  }

  onRatingsChanged(event: any) {
    switch (event.detail.value) {
      case '1': {
        this.selectedRatings = 1;
        break;
      }
      case '2': {
        this.selectedRatings = 2;
        break;
      }
      case '3': {
        this.selectedRatings = 3;
        break;
      }
      case '4': {
        this.selectedRatings = 4;
        break;
      }
      case '5': {
        this.selectedRatings = 5;
        break;
      }
    }
  }

  applyFilters() {
    let tempBooks = this.books;
    console.log('filtering');
    console.log(this.collections);

    switch (this.selectedOwnership) {
      case 'all':
        break;
      case 'owned':
        tempBooks = tempBooks.filter(book => {
          return book.owned;
        });
        break;
      case 'notOwned':
        tempBooks = tempBooks.filter(book => {
          return !book.owned;
        });
        break;
    }

    if (this.selectedRatings != 1) {
      tempBooks = tempBooks.filter(book => {
        return book.rating >= this.selectedRatings;
      });
    }

    let selected = this.status.filter((status) => status.checked);
    let temp2 = [];
    selected.forEach(status => {
      let temp = tempBooks.filter(book => {
        console.log(book.status.toLowerCase(), status.name.toLowerCase());
        return book.status.toLowerCase() === status.name.toLowerCase();
      });
      temp.forEach(book => {
        temp2.push(book);
      });
    });
    tempBooks = temp2;


    // selected = this.collections.filter((collection) => collection.checked);
    // temp2 = [];
    // récupérer un array de collections et leurs books id, et push les id dans array puis filter les books par id
    // tempBooks = temp2;


    this._currentSelection = tempBooks;
    return tempBooks;
  }
}
