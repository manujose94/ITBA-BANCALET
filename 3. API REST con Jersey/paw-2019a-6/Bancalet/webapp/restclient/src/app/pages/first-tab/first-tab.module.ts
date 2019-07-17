import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Routes, RouterModule } from '@angular/router';

import { IonicModule } from '@ionic/angular';

import { FirstTabPage } from './first-tab.page';

const routes: Routes = [
  {
    path: 'tabs',
    component: FirstTabPage,
    children: [
      {
        path: 'tab1',
        loadChildren: '../item/items/items.module#ItemsPageModule'
      },
      {
        path: 'tab2',
        loadChildren: '../item/myitems/myitems.module#MyitemsPageModule'
      }
    ]
  },
  {
    path: '',
    redirectTo: 'tabs/tab1',
    pathMatch: 'full'
  },
];

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    RouterModule.forChild(routes)
  ],
  declarations: [FirstTabPage]
})
export class FirstTabPageModule {}
