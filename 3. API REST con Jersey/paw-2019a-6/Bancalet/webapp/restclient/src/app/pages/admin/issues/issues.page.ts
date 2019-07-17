import { Component, OnInit } from '@angular/core';
import { AdminService } from 'src/app/services/admin.service';
import { AlertController, Events, LoadingController } from '@ionic/angular';
@Component({
  selector: 'app-issues',
  templateUrl: './issues.page.html',
  styleUrls: ['./issues.page.scss'],
})
export class IssuesPage implements OnInit {
  tipoSelected;
  issues;
  searchIssue = '';
  type: 0;
  onFiltro = false;
  constructor(public alertController: AlertController,
    public loadingCtrl: LoadingController,
    public events: Events,
    private adminService: AdminService) { }
    tipos =  this.adminService.getTipos();
  ngOnInit() {
    this.getIssues();
  }
  async getIssues() {
    const loading = await this.loadingCtrl.create({
      message: 'Cargando issues..',
      translucent: true,
    });
    await loading.present();
    this.adminService.getIssues().then(value => {
      this.issues = value.data.ayudas.entry;
         loading.dismiss();
      }).catch(error => {
        if (!error.response) { error.response = 'ERROR'; }
        loading.dismiss();
    });
  }
  async getIssuesFiltro(name: string , tipo: any) {
    const loading = await this.loadingCtrl.create({
      message: 'Cargando filtros...',
      translucent: true,
    });
    await loading.present();
    name = this.searchIssue;
    this.adminService.getIssuesFiltro(name, tipo).then(value => {
      this.issues = value.data.ayudas.entry;
      this.tipoSelected = 0;
         loading.dismiss();
      }).catch(error => {
        loading.dismiss();
    });
  }
  searchTipoChanged() {
    this.onFiltro = true;
    this.tipoSelected = this.type;
    this.getIssuesFiltro(this.searchIssue, this.tipoSelected);
  }
    async ionRefresh(event) {
      await  this.getIssues();
      event.target.complete();
    }
    ionViewDidEnter (){
      this.getIssues();
    }
}