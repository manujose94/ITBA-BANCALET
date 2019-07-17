import { Component, OnInit } from '@angular/core';
import { ItemsService } from 'src/app/services/items.service';
import { LoadingController } from '@ionic/angular';
import { UsersService } from 'src/app/services/users.service';
@Component({
  selector: 'app-myitems',
  templateUrl: './myitems.page.html',
  styleUrls: ['./myitems.page.scss'],
})
export class MyitemsPage implements OnInit {
  items;
  currentUser;
  itemsSize = '0';
  data;
  constructor(public loadingCtrl: LoadingController,
    private itemsService: ItemsService, private usersService: UsersService) { }
  ngOnInit() {
    this.currentUser = this.usersService.getUserInfo();
    this.getItemsUser();
  }
  async getItemsUser() {
    const loading = await this.loadingCtrl.create({
      message: 'Cargando sus productos..',
      translucent: true,
    });
    await loading.present();
    this.itemsService.getItemsUser().then(value => {
      this.items = value.data.items.entry;
         loading.dismiss();
      }).catch(error => {
        if (!error.response) { error.response = 'ERROR'; }
        loading.dismiss();
    });
}
async getHistorialCompra() {
  const loading = await this.loadingCtrl.create({
    message: 'Cargando sus compras..',
    translucent: true,
  });
  await loading.present();
  this.usersService.getHistorialCompra().then(value => {
    this.items = value.data.items.entry;
    this.itemsSize = value.data.comprasSize;
    if ( value.data.comprasSize == 0) { this.itemsSize = '0'; }
       loading.dismiss();
    }).catch(error => {
      if (!error.response) { error.response = 'ERROR'; }
      loading.dismiss();
  });
}
async getHistorialVentas() {
  const loading = await this.loadingCtrl.create({
    message: 'Cargando sus ventas..',
    translucent: true,
  });
  await loading.present();
  this.usersService.getHistorialVentas().then(value => {
    this.data = value.data;
    this.items = value.data.itemsvendidos.entry;
    this.itemsSize = this.items.size();
    if ( value.data.comprasSize == 0) { this.itemsSize = '0'; }
       loading.dismiss();
    }).catch(error => {
      if (!error.response) { error.response = 'ERROR'; }
      loading.dismiss();
  });
}
ionViewDidEnter (){
  this.getItemsUser();
}
async ionRefresh(event) {
  await  this.getItemsUser();
  event.target.complete();
}
segmentChanged(ev: any) {
  if (ev.detail.value == 'myitems') {
    this.getItemsUser();
  } else if (ev.detail.value == 'mysales') {
    this.getHistorialCompra();
  } else {
    this.getHistorialVentas();
  }
}
}
