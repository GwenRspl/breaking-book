export class Friend {
  id: number;
  name: string;
  avatar: string;
  userId: number;
  historyBookIds: number[];


  constructor(name: string, avatar: string, userId: number) {
    this.name = name;
    this.avatar = avatar;
    this.userId = userId;
  }
}
