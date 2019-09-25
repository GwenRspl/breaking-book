import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot} from '@angular/router';
import {Observable} from 'rxjs';
import {BooksService} from './books.service';
import {Book} from '../book.model';

@Injectable()
export class BookResolver implements Resolve<Book> {

  constructor(private booksService: BooksService) {
  }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Book> | Promise<Book> | Book {
    return this.booksService.getBookById(route.params['bookId']);
  }

}
