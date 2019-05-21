import {Component, OnInit, ViewChild} from '@angular/core';
import {Book} from './book.model';
import {ActionSheetController, IonSlides} from '@ionic/angular';
import {BooksService} from './services/books.service';
import {TokenStorageService} from '../authentication/services/token-storage.service';
import {Router} from '@angular/router';
import {Collection} from '../collections/collection.model';

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
  private _collections: Collection[] = [];
  private _currentlyReading: Book[] = [];
  private _collectionsCheckbox: CollectionCheckbox[] = [];
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

  get collections(): Collection[] {
    return this._collections;
  }

  get collectionsCheckbox(): CollectionCheckbox[] {
    return this._collectionsCheckbox;
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

  retrieveCurrentlyReading() {
    this._currentlyReading = this.books.filter(book => book.status === 'ONGOING');
  }

  retrieveCollections() {
    this.booksService.getCollections(this.userId).subscribe(
      data => {
        this._collections = data;
        this._collectionsCheckbox = [];
        let i: number;
        for (i = 0; i < this.collections.length; i++) {
          this._collectionsCheckbox.push({id: i, name: this.collections[i].name, checked: false});
        }
      },
      error => {
        console.log(error);
      }
    );
  }

  showBookDetails(bookId: number) {
    this.router.navigate((['/', 'library', 'show', bookId]));
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

  /*
  Carousel methods
   */
  nextSlide(slideView) {
    slideView.slideNext(500);
  }

  previousSlide(slideView) {
    slideView.slidePrev(500);
  }

  /*
  Filters methods
   */

  searchBarFiltering() {
    this._currentSelection = this.applyFilters().filter(book => {
      return book.title.toLowerCase().indexOf(this.userInput.toLowerCase()) > -1 ||
        book.authors.toString().toLowerCase().indexOf(this.userInput.toLowerCase()) > -1;
    });

  }

  applyFilters() {
    let tempBooks = this.books;

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
        return book.status.toLowerCase() === status.name.toLowerCase();
      });
      temp.forEach(book => {
        temp2.push(book);
      });
    });
    tempBooks = temp2;


    selected = this.collectionsCheckbox.filter((collection) => collection.checked);
    if (selected.length != 0) {
      temp2 = [];
      selected.forEach(collectionCheckbox => {
        this.collections.forEach(collection => {
          if (collection.name == collectionCheckbox.name) {
            collection.booksIds.forEach(book => {
              if (!temp2.includes(book)) {
                temp2.push(book);
              }
            })
          }
        });
      });
      console.log(temp2);

      tempBooks = tempBooks.filter(book => {
        return temp2.includes(book.id);
      });

    }


    this._currentSelection = tempBooks;
    return tempBooks;
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

  /**
   *  Sorting methods
   */

  sortBooks(event: any) {
    console.log(event);
    switch (event.detail.value) {
      case 'Title':
        this.sortByTitle();
        break;
      case 'Author':
        this.sortByAuthor();
        break;
      case 'Rating':
        this.sortByRating();
        break;
    }
  }

  sortByTitle() {
    this.currentSelection.sort((a: Book, b: Book) => {
      if (a.title < b.title) {
        return -1;
      } else if (a.title > b.title) {
        return 1;
      } else {
        return 0;
      }
    });
  }

  sortByAuthor() {
    this.currentSelection.sort((a: Book, b: Book) => {
      if (a.authors < b.authors) {
        return -1;
      } else if (a.authors > b.authors) {
        return 1;
      } else {
        return 0;
      }
    });
  }

  sortByRating() {
    this.currentSelection.sort((a: Book, b: Book) => {
      if (a.rating < b.rating) {
        return 1;
      } else if (a.rating > b.rating) {
        return -1;
      } else {
        return 0;
      }
    });
  }

}
