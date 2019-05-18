import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {RouterModule, Routes} from '@angular/router';

import {IonicModule} from '@ionic/angular';

import {AccountPage} from './account.page';
import {EditAccountComponent} from "./edit-account/edit-account.component";
import {AccountService} from "./services/account.service";

const routes: Routes = [
    {
        path: '',
        component: AccountPage
    }
];

@NgModule({
    imports: [
        CommonModule,
        FormsModule,
        IonicModule,
        RouterModule.forChild(routes),
        ReactiveFormsModule
    ],
    providers: [AccountService],
    declarations: [AccountPage, EditAccountComponent],
    entryComponents: [EditAccountComponent]
})
export class AccountPageModule {
}
