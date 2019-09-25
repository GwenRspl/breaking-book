import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot} from '@angular/router';
import {Observable} from 'rxjs';

import {Friend} from '../friend.model';
import {FriendsService} from './friends.service';

@Injectable()
export class FriendResolver implements Resolve<Friend> {

  constructor(private friendsService: FriendsService) {
  }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Friend> | Promise<Friend> | Friend {
    return this.friendsService.getFriendById(route.params['friendId']);
  }

}
