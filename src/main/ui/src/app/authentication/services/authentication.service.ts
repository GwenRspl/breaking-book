import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';
import {SignUpInfo} from '../sign-up-info';
import {SignInInfo} from '../sign-in-info';
import {JwtResponse} from '../jwt-response';
import {User} from '../user.model';
import {BASE_URL_API} from '../../../environments/environment';

const httpOptions = {
  headers: new HttpHeaders({'Content-Type': 'application/json'})
};

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {
  private signUpURL = BASE_URL_API + '/auth/signup';
  private signInURL = BASE_URL_API + '/auth/signin';
  private usersURL = BASE_URL_API + '/users/username/';

  constructor(private httpClient: HttpClient) {
  }

  signUp(info: SignUpInfo): Observable<string> {
    return this.httpClient.post<string>(this.signUpURL, info, httpOptions);
  }

  attemptAuthentication(credentials: SignInInfo): Observable<JwtResponse> {
    return this.httpClient.post<JwtResponse>(this.signInURL, credentials, httpOptions);
  }

  getUserByUsername(username: string) {
    const path = this.usersURL + username;
    return this.httpClient.get<User>(path);
  }
}
