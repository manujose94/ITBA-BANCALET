import { Component, OnInit } from '@angular/core';
import { AdminService } from 'src/app/services/admin.service';
import { AlertController, Events, LoadingController } from '@ionic/angular';
@Component({
  selector: 'app-users',
  templateUrl: './users.page.html',
  styleUrls: ['./users.page.scss'],
})
export class UsersPage implements OnInit {
  tipoSelected;
  users;
  searchuser = '';
  type: 0;
  onFiltro = false;
  constructor(public alertController: AlertController,
    public loadingCtrl: LoadingController,
    public events: Events,
    private adminService: AdminService) { }
    ngOnInit() {
      this.getUsers();
    }
    async getUsers() {
      const loading = await this.loadingCtrl.create({
        message: 'Cargando users..',
        translucent: true,
      });
      await loading.present();
      this.adminService.getAllUsersAdmin().then(value => {
        this.users = value.data.users.entry;
           loading.dismiss();
        }).catch(error => {
          if (!error.response) { error.response = 'ERROR'; }
          loading.dismiss();
      });
    }
    async getAllUsersAdminFiltro(name: string , role: any) {
      const loading = await this.loadingCtrl.create({
        message: 'Cargando filtros...',
        translucent: true,
      });
      await loading.present();
      name = this.searchuser;
      this.adminService.getAllUsersAdminFiltro(name, role).then(value => {
        this.users = value.data.users.entry;
        this.tipoSelected = 0;
           loading.dismiss();
        }).catch(error => {
          loading.dismiss();
      });
    }
    searchTipoChanged() {
      this.onFiltro = true;
      this.tipoSelected = this.type;
      this.getAllUsersAdminFiltro(this.searchuser, this.tipoSelected);
    }
async ionRefresh(event) {
        await  this.getUsers();
        event.target.complete();
      }
      ionViewDidEnter (){
        this.getUsers();
      }
}