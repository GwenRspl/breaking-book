import { Component, OnInit } from '@angular/core';
import {Router} from "@angular/router";
import {PopoverController} from "@ionic/angular";

@Component({
  selector: 'app-settings',
  templateUrl: './settings.component.html',
  styleUrls: ['./settings.component.scss'],
})
export class SettingsComponent implements OnInit {

  constructor(private router: Router,
              private popoverCtrl: PopoverController) { }

  ngOnInit() {}

  async closePopover() {
    await this.popoverCtrl.dismiss();
  }
}
