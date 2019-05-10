import { Injectable } from '@angular/core';
import {Subject} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class HeaderService {
  private _updateNavBar = new Subject<string>();

  constructor() { }

  get updateNavBar(): Subject<string> {
    return this._updateNavBar;
  }

  refreshNavBar(bool: string) {
    this.updateNavBar.next(bool);
  }
}
