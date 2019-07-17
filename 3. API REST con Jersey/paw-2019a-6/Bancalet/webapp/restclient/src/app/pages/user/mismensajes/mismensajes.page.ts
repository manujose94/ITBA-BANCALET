import { Component, OnInit } from '@angular/core';
import { AlertController, LoadingController, ToastController } from '@ionic/angular';
import { UsersService } from 'src/app/services/users.service';
import { ItemsService } from 'src/app/services/items.service';
@Component({
  selector: 'app-mismensajes',
  templateUrl: './mismensajes.page.html',
  styleUrls: ['./mismensajes.page.scss'],
})
export class MismensajesPage implements OnInit {
  items: any;
  contactos: any;
  compradores;
  recibidos = true;
  constructor(public alertCtrl: AlertController,
    public loadingCtrl: LoadingController, private usersService: UsersService,
    private itemsService: ItemsService, public toastCtrl: ToastController) { }
  ngOnInit() {
    this.getMisMensajesRecibidos();
  }
  async getMisMensajesRecibidos() {
    const loading = await this.loadingCtrl.create({
      message: 'Cargando mensajes..',
      translucent: true,
    });
    await loading.present();
    this.usersService.getMisMensajesRecibidos().then(value => {
      this.items = value.data.items.entry;
      this.contactos = value.data.contactos.entry;
      if (this.contactos.length === 0) { this.recibidos = false; }
      this.compradores = value.data.listcontactados.entry;
         loading.dismiss();
      }).catch(error => {
        if (!error.response) { error.response = 'ERROR'; }
        loading.dismiss();
    });
}
async ionRefresh(event) {
  await  this.getMisMensajesRecibidos();
  event.target.complete();
}
async onBaja(idItem: any, idComprador: any, item: any) {
  let comprador: any;
  for (const comp of  this.compradores) {
    if ( comp.key === idComprador) {
      comprador =  comp;
    }
  }
    const alert = await this.alertCtrl.create({
      header: 'Baja Producto',
      subHeader: 'Producto: ' + item.value.name ,
      message: 'Asignar producto a <strong>' + comprador.value.username + '</strong>',
      buttons: [
        {
          text: 'Cancelar',
          role: 'cancel',
          cssClass: 'secondary',
        }, {
          text: 'Confirmar',
          handler: async (data) => {
              const loading = await this.loadingCtrl.create({
                message: 'Procesando baja... ',
                translucent: true,
              });
              await loading.present();
              this.itemsService.putBaja(idItem, idComprador).then(value => {
                 loading.dismiss();
                 this.presentToast('Item dado de baja');
                this. getMisMensajesRecibidos();
              }).catch(error => {
                if (!error.response) { error.response = 'ERROR'; }
                  loading.dismiss();
                this.presentToast('Error baja item: ' + error);
            });
          }
        }
      ]
    });
    await alert.present();
  }
async presentToast(text) {
  const toast = await this.toastCtrl.create({
    showCloseButton: true,
    message: text,
    duration: 4000
  });
  toast.present();
}
}
