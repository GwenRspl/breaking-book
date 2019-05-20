import {Component, OnInit} from '@angular/core';
import {GoogleApiBook} from './googleApiBook.model';
import {BooksService} from '../services/books.service';
import {ModalController} from '@ionic/angular';
import {Book} from '../book.model';
import {GoogleApiBookModalComponent} from './google-api-book-modal/google-api-book-modal.component';

@Component({
  selector: 'app-search-via-api',
  templateUrl: './search-via-api.page.html',
  styleUrls: ['./search-via-api.page.scss'],
})
export class SearchViaApiPage implements OnInit {
  public searchInput: string = '';
  private _TITLE = 'intitle';
  private _AUTHOR = 'inauthor';
  private _ISBN = 'isbn';
  private _searchMode: string = this.TITLE;
  private _searchResult: GoogleApiBook[] = [];
  private _submitted = false;

  constructor(private booksService: BooksService,
              private modalCtrl: ModalController) {
  }

  get searchMode(): string {
    return this._searchMode;
  }


  get submitted(): boolean {
    return this._submitted;
  }

  get TITLE(): string {
    return this._TITLE;
  }

  get AUTHOR(): string {
    return this._AUTHOR;
  }

  get ISBN(): string {
    return this._ISBN;
  }

  get searchResult(): GoogleApiBook[] {
    return this._searchResult;
  }

  ngOnInit() {
  }

  segmentChanged(event: any) {
    this._searchMode = event.detail.value;
  }

  search() {
    this.booksService.searchBookViaGoogleApi(this.searchMode, this.searchInput).subscribe(
      data => {
        this._submitted = true;
        console.log(data);
        if (data.totalItems == 0) {
          this._searchResult = [];
        } else {
          this._searchResult = data.items;
        }
      },
      error => {
        this._searchResult = [];
        console.log(error);
      }
    )
  }

  selectBook(googleBook: GoogleApiBook) {
    let isbn = null;
    if (googleBook.volumeInfo.industryIdentifiers != null) {
      isbn = googleBook.volumeInfo.industryIdentifiers[0].identifier;
    }

    let thumbnail = null;
    if (googleBook.volumeInfo.imageLinks != null) {
      thumbnail = googleBook.volumeInfo.imageLinks.thumbnail;
    }
    const convertedBook = new Book(null,
      googleBook.volumeInfo.title,
      googleBook.volumeInfo.authors,
      isbn,
      thumbnail,
      googleBook.volumeInfo.language.toUpperCase(),
      googleBook.volumeInfo.publisher,
      new Date(googleBook.volumeInfo.publishedDate),
      googleBook.volumeInfo.pageCount,
      googleBook.volumeInfo.description,
      null, null, null, null, null, null);
    this.modalCtrl
      .create({
        component: GoogleApiBookModalComponent,
        componentProps: {book: convertedBook}
      })
      .then(modal => {
        modal.present();
        return modal.onDidDismiss();
      })

  }
}
