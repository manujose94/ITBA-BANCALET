import { Component, OnInit } from '@angular/core';
import { UsersService } from 'src/app/services/users.service';
import { LoadingController, AlertController } from '@ionic/angular';
@Component({
  selector: 'app-miscontactados',
  templateUrl: './miscontactados.page.html',
  styleUrls: ['./miscontactados.page.scss'],
})
export class MiscontactadosPage implements OnInit {
  items;
  contactos;
  sellers;
  enviados = true;
  constructor(public alertController: AlertController,
    public loadingCtrl: LoadingController, private usersService: UsersService) { }
  ngOnInit() {
    this.getMisMensajesEnviados();
  }
  async getMisMensajesEnviados() {
    const loading = await this.loadingCtrl.create({
      message: 'Cargando mensajes..',
      translucent: true,
    });
    await loading.present();
    this.usersService.getMisMensajesEnviados().then(value => {
      this.items = value.data.items.entry;
      this.contactos = value.data.contactos.entry;
      this.sellers = value.data.listcontactados.entry;
      if (this.contactos.length === 0) { this.enviados = false; }
         loading.dismiss();
      }).catch(error => {
        if (!error.response) { error.response = 'ERROR'; }
        loading.dismiss();
    });
}
async ionRefresh(event) {
  await  this.getMisMensajesEnviados();
  event.target.complete();
}
}
