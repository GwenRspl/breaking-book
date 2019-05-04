import { Component, OnInit } from '@angular/core';
import {PopoverController} from "@ionic/angular";
import {SettingsComponent} from "./settings/settings.component";
import {Router} from "@angular/router";

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss'],
})
export class HeaderComponent implements OnInit {

  constructor(private popoverCtrl: PopoverController,
              private router: Router) { }

  ngOnInit() {}

  async onCogClick(ev: any){
    const popover = await this.popoverCtrl.create({
      component: SettingsComponent,
      event: ev
    });
    return await popover.present();
  }

    goToHome() {
        this.router.navigateByUrl('/home');
    }
}
