import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Routes, RouterModule } from '@angular/router';

import { IonicModule } from '@ionic/angular';

import { ArchivedIssuesPage } from './archived-issues.page';

const routes: Routes = [
  {
    path: '',
    component: ArchivedIssuesPage
  }
];

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    RouterModule.forChild(routes)
  ],
  declarations: [ArchivedIssuesPage]
})
export class ArchivedIssuesPageModule {}
