import {Injectable} from '@angular/core';
import {EditUserInfo} from "../edit-user-info";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";
import {User} from "../../authentication/user.model";

const BASE_URL = 'http://localhost:8080/api/users/';
const httpOptions = {headers: new HttpHeaders({'Content-Type': 'application/json',}), responseType: 'text' as 'json'};


@Injectable({
    providedIn: 'root'
})
export class AccountService {

    constructor(private httpClient: HttpClient) {
    }

    getUser(userId: number): Observable<User> {
        const url = BASE_URL + userId;
        return this.httpClient.get<User>(url);
    }

    editAccount(editUserInfo: EditUserInfo, userId: number): Observable<string> {
        const url = BASE_URL + userId;
        return this.httpClient.put<string>(url, editUserInfo, httpOptions);
    }
}
