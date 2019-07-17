import { Component, OnInit } from '@angular/core';
import { ItemsService } from 'src/app/services/items.service';
import { AlertController, Events, LoadingController } from '@ionic/angular';
@Component({
  selector: 'app-items',
  templateUrl: './items.page.html',
  styleUrls: ['./items.page.scss'],
})
export class ItemsPage implements OnInit {
  items;
  tipoSelected;
  searchItem = '';
  min;
  max;
  today: string;
  slider: any = {lower: 0, upper: 0};
  type: any;
  itemTipoCaducidad;
  fecha_caducidad;
  onFiltro = false;
  constructor(public alertController: AlertController,
     public loadingCtrl: LoadingController,
     public events: Events,
     private itemsService: ItemsService) { }
  tipos =  this.itemsService.getTipos();
  ngOnInit() {
    this.initActualDate();
   this.getItems();
  }
  initActualDate() {
    const today = new Date();
    const day = new Date().getDate();
   const mounth = today.getMonth() + 1;
   let  dd: string;
   let mm: string;
    const yyyy = today.getFullYear();
    if (day < 10) { dd = '0' + day; } else { dd = day.toString(); }
    if (mounth < 10) {
      mm = '0' + mounth;
    } else { mm = mounth.toString(); }
    this.today = yyyy + '-' + mm + '-' + dd;
  }
  async showError(text) {
    const alert = await this.alertController.create({
      header: 'Problema',
      subHeader: 'Algo ha ocurrido a obtener items',
      message: text,
      buttons: ['OK']
    });
    await alert.present();
  }
onSliderChange(ev: any) {
    this.slider = ev;
}
  searchTipoChanged() {
    this.onFiltro = true;
    this.tipoSelected = this.type;
    if (this.fecha_caducidad) { this.fecha_caducidad = this.fecha_caducidad.substring(0, 11); }
    this.getItemsFiltro(this.searchItem, this.tipoSelected,
      this.slider.lower, this.slider.upper, this.itemTipoCaducidad, this.fecha_caducidad );
  }
  itemTipoCaducidadChanged() {
    this.onFiltro = true;
  }
  async getItems() {
    const loading = await this.loadingCtrl.create({
      message: 'Cargando productos..',
      translucent: true,
    });
    await loading.present();
    this.itemsService.getItems().then(value => {
      this.max = value.data.max;
      this.min = value.data.min;
      this.slider = {lower: this.min, upper: this.max};
      this.items = value.data.items.entry;
         loading.dismiss();
      }).catch(error => {
        if (!error.response) { error.response = 'ERROR'; }
        loading.dismiss();
    });
}
async getItemsFiltro(name: string , tipo: any, min: any, max: any,  itemTipoCaducidad: any, fecha_caducidad: any) {
  const loading = await this.loadingCtrl.create({
    message: 'Cargando filtros...',
    translucent: true,
  });
  await loading.present();
  this.itemsService.getItemsFiltro(name, tipo, min, max, itemTipoCaducidad, fecha_caducidad ).then(value => {
    this.items = value.data.items.entry;
       loading.dismiss();
    }).catch(error => {
      if (!error.response) { error.response = 'ERROR'; }
      loading.dismiss();
  });
}
  async ionRefresh(event) {
    await  this.getItems();
    event.target.complete();
  }
  ionViewDidEnter (){
    this.getItems();
  }
    ionPull(event) {
    }
    ionStart(event) {
    }
    ionViewCanEnter() {
    }
    ionViewWillEnter() {
    }
}
