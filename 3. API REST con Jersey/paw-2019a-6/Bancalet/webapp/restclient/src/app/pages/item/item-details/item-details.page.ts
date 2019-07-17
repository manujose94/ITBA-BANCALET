import { UsersService } from './../../../services/users.service';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ItemsService } from 'src/app/services/items.service';
import { AlertController, LoadingController, ToastController } from '@ionic/angular';
@Component({
  selector: 'app-item-details',
  templateUrl: './item-details.page.html',
  styleUrls: ['./item-details.page.scss'],
})
export class ItemDetailsPage implements OnInit {
  item;
  seller;
  itemID;
  userID;
  constructor(private activatedRoute: ActivatedRoute, private itemsService: ItemsService,
    public alertCtrl: AlertController, public loadingCtrl: LoadingController, private usersService: UsersService,
    public toastCtrl: ToastController) { }
  ngOnInit() {
  const id = this.activatedRoute.snapshot.paramMap.get('id');
  this.itemID = id;
  this.userID = this.usersService.getIdUserInfo();
  this.itemsService.getItemsID(id).then(value => {
    this.item = value.data.item;
    this.seller = value.data.seller;
  }).catch(error => {
  if (!error.response) { error.response = 'ERROR'; }
});
}
async onEdit(e: any) {
  }
async onContactar(e: any) {
    const alert = await this.alertCtrl.create({
      header: 'Contactar con ' + this.seller.username,
      message: 'Introduzca un mensaje para el vendedor',
      inputs: [
        {
          name: 'mensaje',
          type: 'text',
          placeholder: 'Mensaje'
        }
      ],
      buttons: [
        {
          text: 'Cancelar',
          role: 'cancel',
          cssClass: 'secondary',
        }, {
          text: 'Confirmar',
          handler: async (data) => {
              const loading = await this.loadingCtrl.create({
                message: 'Enviando mensaje.. ',
                translucent: true,
              });
              await loading.present();
              this.itemsService.putContactar(this.itemID, data.mensaje).then(value => {
                 loading.dismiss();
                this.presentToast(value.data.mensaje);
              }).catch(error => {
                if (!error.response) { error.response = 'ERROR'; }
                  loading.dismiss();
                this.presentToast('Error al contactar el producto: ' + this.item.name);
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
async onDeleteItem(itemID){
  const alert = await this.alertCtrl.create({
    header: ' ¿Desea eliminar el producto? ',
    buttons: [
      {
        text: 'Cancelar',
        role: 'cancel',
        cssClass: 'secondary',
      }, {
        text: 'Confirmar',
        handler: async (data) => {
            const loading = await this.loadingCtrl.create({
              message: 'Eliminando producto.. ',
              translucent: true,
            });
            await loading.present();
            this.itemsService.delete(this.itemID).then(value => {
               loading.dismiss();
              this.presentToast(value.data.mensaje);
            }).catch(error => {
              if (!error.response) { error.response = 'ERROR'; }
                loading.dismiss();
              this.presentToast('Error al eliminar el producto: ' + this.item.name);
          });
        }
      }
    ]
  });
  await alert.present();
}
async onRate(e: any) {
  const alert = await this.alertCtrl.create({
    header: 'Valorar a ' + this.seller.username,
    message: 'Introduzca un comentario sobre el artículo',
    inputs: [
      {
        name: 'valoracion',
        type: 'text',
        placeholder: 'Valoracion'
      },
      {
        name: 'estrellas',
        type: 'text',
        placeholder: 'Estrellas'
      }
    ],
    buttons: [
      {
        text: 'Cancelar',
        role: 'cancel',
        cssClass: 'secondary',
      }, {
        text: 'Confirmar',
        handler: async (data) => {
            const loading = await this.loadingCtrl.create({
              message: 'Enviando valoracion.. ',
              translucent: true,
            });
            await loading.present();
            let formRate = {
              valoracion: data.valoracion,
              estrellas: data.estrellas,
           }; 
            this.itemsService.putRate(this.itemID, formRate).then(value => {
               loading.dismiss();
              this.presentToast('Gracias por tu valoración!');
            }).catch(error => {
              if (!error.response) { error.response = 'ERROR'; }
                loading.dismiss();
              if (error.toString().includes("403")) {
                this.presentToast('Ha surgido un problema: No puedes valorar este artículo.');
              }
              if (error.toString().includes("404")) {
                this.presentToast('Ha surgido un problema: El producto no existe.');
              }
              if (error.toString().includes("409")) {
                this.presentToast('Ha surgido un problema: La valoracion no puede estar vacía.');
              }
          });
        }
      }
    ]
  });
  await alert.present();
}
}
