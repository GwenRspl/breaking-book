export class Wishlist {
  id: number;
  name: string;
  userId: number;
  booksIds: number[];


  constructor(name: string, userId: number) {
    this.name = name;
    this.userId = userId;
  }
}
