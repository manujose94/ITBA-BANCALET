import { NgModule } from '@angular/core';
import { PreloadAllModules, RouterModule, Routes } from '@angular/router';

const routes: Routes = [
  { path: '', loadChildren: './pages/login/login.module#LoginPageModule' },
  { path: 'menu', loadChildren: './pages/menu/menu.module#MenuPageModule' },
  { path: 'register', loadChildren: './pages/register/register.module#RegisterPageModule' },
  { path: 'additem', loadChildren: './pages/item/additem/additem.module#AdditemPageModule' },
  { path: 'items', loadChildren: './pages/item/items/items.module#ItemsPageModule' },
  { path: 'myitems', loadChildren: './pages/item/myitems/myitems.module#MyitemsPageModule' },
  { path: 'first-tab', loadChildren: './pages/first-tab/first-tab.module#FirstTabPageModule' },
  { path: 'item-details/:id', loadChildren: './pages/item/item-details/item-details.module#ItemDetailsPageModule' },
  { path: 'user/:id', loadChildren: './pages/user/user/user.module#UserPageModule' },
  { path: 'miscontactados', loadChildren: './pages/user/miscontactados/miscontactados.module#MiscontactadosPageModule' },
  { path: 'mismensajes', loadChildren: './pages/user/mismensajes/mismensajes.module#MismensajesPageModule' },
  { path: 'misrecomendaciones', loadChildren: './pages/user/misrecomendaciones/misrecomendaciones.module#MisrecomendacionesPageModule' },
  { path: 'edititem/:id', loadChildren: './pages/item/edititem/edititem.module#EdititemPageModule' },
  { path: 'confirmregister/:id', loadChildren: './pages/confirmregister/confirmregister.module#ConfirmregisterPageModule' },
  { path: 'ayuda', loadChildren: './pages/user/ayuda/ayuda.module#AyudaPageModule' },
  { path: 'issues', loadChildren: './pages/admin/issues/issues.module#IssuesPageModule' },
  { path: 'users', loadChildren: './pages/admin/users/users.module#UsersPageModule' },
  { path: 'archived-issues', loadChildren: './pages/admin/archived-issues/archived-issues.module#ArchivedIssuesPageModule' },
  { path: 'tabs-admin', loadChildren: './pages/admin/tabs-admin/tabs-admin.module#TabsAdminPageModule' },
  { path: 'edituser/:id', loadChildren: './pages/user/edituser/edituser.module#EdituserPageModule' },
  { path: 'editpass/:id', loadChildren: './pages/user/editpass/editpass.module#EditpassPageModule' },
  { path: 'issue-details/:id', loadChildren: './pages/admin/issue-details/issue-details.module#IssueDetailsPageModule' }
];

@NgModule({
  imports: [
    RouterModule.forRoot(routes, { preloadingStrategy: PreloadAllModules })
  ],
  exports: [RouterModule]
})
export class AppRoutingModule { }
