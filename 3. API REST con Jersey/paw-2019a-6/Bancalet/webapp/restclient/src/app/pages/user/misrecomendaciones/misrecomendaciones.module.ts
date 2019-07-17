import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Routes, RouterModule } from '@angular/router';

import { IonicModule } from '@ionic/angular';

import { MisrecomendacionesPage } from './misrecomendaciones.page';

const routes: Routes = [
  {
    path: '',
    component: MisrecomendacionesPage
  }
];

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    RouterModule.forChild(routes)
  ],
  declarations: [MisrecomendacionesPage]
})
export class MisrecomendacionesPageModule {}
