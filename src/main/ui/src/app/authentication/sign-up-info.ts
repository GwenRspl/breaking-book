export class SignUpInfo {

  private username: string;
  private email: string;
  private password: string;
  private role: string;

  constructor(username: string, email: string, password: string) {
    this.username = username;
    this.email = email;
    this.password = password;
    this.role = 'user';
  }

}
