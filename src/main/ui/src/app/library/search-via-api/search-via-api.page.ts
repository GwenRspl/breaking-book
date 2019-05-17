import {Component, OnInit} from '@angular/core';
import {GoogleApiBook} from './googleApiBook.model';
import {BooksService} from '../services/books.service';

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
  private _searchResult: GoogleApiBook[];

  constructor(private booksService: BooksService) {
  }

  get searchMode(): string {
    return this._searchMode;
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
    console.log(this.searchInput);
    this.booksService.searchBookViaGoogleApi(this.searchMode, this.searchInput).subscribe(
      data => {
        console.log(data);
        this._searchResult = data.items;
        console.log(this.searchResult);
      },
      error => {
        console.log(error);
      }
    )
  }
}
