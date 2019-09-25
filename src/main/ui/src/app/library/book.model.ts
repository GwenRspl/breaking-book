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
  status: string;
  rating: number;
  comment: string;
  friendId: number;
  userId: number;


  constructor(id: number, title: string, authors: string[], isbn: string, image: string, language: string, publisher: string, datePublished: Date, pages: number, synopsis: string, owned: boolean, status: string, rating: number, comment: string, friend: number, user: number) {
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
    this.status = status;
    this.rating = rating;
    this.comment = comment;
    this.friendId = friend;
    this.userId = user;
  }
}
