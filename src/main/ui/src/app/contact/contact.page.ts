import {Component, OnInit} from '@angular/core';

@Component({
  selector: 'app-contact',
  templateUrl: './contact.page.html',
  styleUrls: ['./contact.page.scss'],
})
export class ContactPage implements OnInit {
  avatarAgathe: string = '../../assets/avatar_a.jpeg';
  avatarGwen: string = '../../assets/avatar_g.jpeg';

  constructor() {
  }

  ngOnInit() {
  }

}
