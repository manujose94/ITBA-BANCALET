import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Routes, RouterModule } from '@angular/router';

import { IonicModule } from '@ionic/angular';

import { MenuPage } from './menu.page';

const routes: Routes = [
  {
    path: '',
    redirectTo: 'first',
    pathMatch: 'full'
  },
  {
  path: '',
  component: MenuPage,
  children: [
    {
      path: 'first',
      loadChildren: '../first-tab/first-tab.module#FirstTabPageModule'
    },
    {
      path: 'misrecomendaciones',
      loadChildren: '../user/misrecomendaciones/misrecomendaciones.module#MisrecomendacionesPageModule'
    },
    {
      path: 'mismensajes',
      loadChildren: '../user/mismensajes/mismensajes.module#MismensajesPageModule'
    },
    {
      path: 'miscontactados',
      loadChildren: '../user/miscontactados/miscontactados.module#MiscontactadosPageModule'
    },
    {
      path: 'ayuda',
      loadChildren: '../user/ayuda/ayuda.module#AyudaPageModule'
    }
   
  ]
}
];

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    RouterModule.forChild(routes)
  ],
  declarations: [MenuPage]
})
export class MenuPageModule {}
