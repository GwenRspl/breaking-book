import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot} from '@angular/router';
import {Injectable} from '@angular/core';
import {TokenStorageService} from './token-storage.service';

@Injectable()
export class AuthGuard implements CanActivate {

  constructor(private tokenStorageService: TokenStorageService,
              private router: Router) {
  }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
    if (!this.tokenStorageService.isAuthenticated()) {
      this.router.navigate(['sign-in']);
      return false;
    }
    return true;
  }
}
