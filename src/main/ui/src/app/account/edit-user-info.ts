export class EditUserInfo {
    email: string;
    password: string;
    avatar: string;

    constructor(email: string, password: string, avatar: string) {
        this.email = email;
        this.password = password;
        this.avatar = avatar;
    }
}
