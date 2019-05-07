import {Friend} from './friend.model';
import {User} from './user.model';

export class Book {
  id: number;
  title: string;
  authors: string[];
  isbn: string;
  image: string;
  language: string;
  publisher: string;
  datePublished: Date;
  pages: number;
  synopsis: string;
  owned: boolean;
  read: boolean;
  rating: number;
  comment: string;
  friend: Friend;
  user: User;


  constructor(id: number, title: string, authors: string[], isbn: string, image: string, language: string, publisher: string, datePublished: Date, pages: number, synopsis: string, owned: boolean, read: boolean, rating: number, comment: string, friend: Friend, user: User) {
    this.id = id;
    this.title = title;
    this.authors = authors;
    this.isbn = isbn;
    this.image = image;
    this.language = language;
    this.publisher = publisher;
    this.datePublished = datePublished;
    this.pages = pages;
    this.synopsis = synopsis;
    this.owned = owned;
    this.read = read;
    this.rating = rating;
    this.comment = comment;
    this.friend = friend;
    this.user = user;
  }
}
