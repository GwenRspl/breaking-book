import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Book} from '../book.model';

@Injectable({
  providedIn: 'root'
})
export class BooksService {

  private baseURL: string = 'http://localhost:8080/api/books';

  constructor(private httpClient: HttpClient) { }

  getBooks(userId: number){
    return this.httpClient.get<Book[]>(this.baseURL);
  }
}
