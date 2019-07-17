import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Routes, RouterModule } from '@angular/router';

import { IonicModule } from '@ionic/angular';

import { TabsAdminPage } from './tabs-admin.page';

const routes: Routes = [
  {
    path: 'tabs',
    component: TabsAdminPage,
    children: [
      {
        path: 'tab1',
        loadChildren: '../issues/issues.module#IssuesPageModule'
      },
      {
        path: 'tab2',
        loadChildren: '../archived-issues/archived-issues.module#ArchivedIssuesPageModule'
      },
      {
        path: 'tab3',
        loadChildren: '../users/users.module#UsersPageModule'
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
  declarations: [TabsAdminPage]
})
export class TabsAdminPageModule {}
