import { Component, OnInit } from '@angular/core';
import { UsersService } from 'src/app/services/users.service';
import { AlertController, Events, LoadingController } from '@ionic/angular';
@Component({
  selector: 'app-misrecomendaciones',
  templateUrl: './misrecomendaciones.page.html',
  styleUrls: ['./misrecomendaciones.page.scss'],
})
export class MisrecomendacionesPage implements OnInit {
  items;
  recibidos = true;
  constructor(public alertController: AlertController,
    public loadingCtrl: LoadingController,
    public events: Events,
    private userService: UsersService) { }
  ngOnInit() {
    this.getItems();
  }
  async getItems() {
    const loading = await this.loadingCtrl.create({
      message: 'Cargando productos..',
      translucent: true,
    });
    await loading.present();
    this.userService.getMisSugerencias().then(value => {
      this.items = value.data.items.entry;
      if (this.items.length === 0) { this.recibidos = false; }
         loading.dismiss();
      }).catch(error => {
        if (!error.response) { error.response = 'ERROR'; }
        this.recibidos=false;
        loading.dismiss();
    });
}
async ionRefresh(event) {
  await  this.getItems();
  event.target.complete();
}
ionPull(event) {
  }
ionStart(event) {
  }
}

