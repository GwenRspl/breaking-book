import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";
import {SignUpInfo} from "../sign-up-info.model";

const httpOptions ={
  headers: new HttpHeaders({'Content-Type': 'application/json'})
};

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {
  private signUpURL: string = 'http://localhost:8080/api/auth/signup';

  constructor(private httpClient: HttpClient) { }

  signUp(info: SignUpInfo): Observable<string> {
    return this.httpClient.post<string>(this.signUpURL, info, httpOptions);
  }
}
