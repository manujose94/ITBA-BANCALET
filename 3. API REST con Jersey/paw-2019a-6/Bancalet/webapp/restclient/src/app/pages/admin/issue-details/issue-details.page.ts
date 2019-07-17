import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { AdminService } from 'src/app/services/admin.service';
import { AlertController, LoadingController, ToastController,NavController } from '@ionic/angular';
@Component({
  selector: 'app-issue-details',
  templateUrl: './issue-details.page.html',
  styleUrls: ['./issue-details.page.scss'],
})
export class IssueDetailsPage implements OnInit {
  issue;
  issueID;
  constructor(private activatedRoute: ActivatedRoute,public navCtrl: NavController,
    public alertCtrl: AlertController, public loadingCtrl: LoadingController, private adminService: AdminService,
    public toastCtrl: ToastController) { }
  ngOnInit() {
  const id = this.activatedRoute.snapshot.paramMap.get('id');
  this.issueID = id;
  this.getIssueID(id)
}
async getIssueID(id) {
  const loading = await this.loadingCtrl.create({
    message: 'Cargando issue..',
    translucent: true,
  });
  await loading.present();
  this.adminService.getIssuesID(id).then(value => {
    this.issue = value.data.issue;
    loading.dismiss();
    }).catch(error => {
      if (!error.response) { error.response = 'ERROR'; }
      loading.dismiss();
  });
}
async presentToast(text) {
  const toast = await this.toastCtrl.create({
    showCloseButton: true,
    message: text,
    duration: 4000
  });
  toast.present();
}
async onDeleteIssue(){
  const alert = await this.alertCtrl.create({
    header: ' ¿Desea eliminar la consulta? ',
    buttons: [
      {
        text: 'Cancelar',
        role: 'cancel',
        cssClass: 'secondary',
      }, {
        text: 'Confirmar',
        handler: async (data) => {
            const loading = await this.loadingCtrl.create({
              message: 'Eliminando consulta.. ',
              translucent: true,
            });
            await loading.present();
            this.adminService.delete(this.issueID).then(value => {
              loading.dismiss();
              this.presentToast('Consulta eliminada');
              this.navCtrl.navigateRoot('/tabs-admin');
            }).catch(error => {
              if (!error.response) { error.response = 'ERROR'; }
                loading.dismiss();
                this.presentToast('Error al eliminar la consulta');
                this.navCtrl.navigateRoot('/tabs-admin');
          });
        }
      }
    ]
  });
  await alert.present();
}
  async onArchivar() {
    const alert = await this.alertCtrl.create({
      header: 'Responder a '+ this.issue.name,
      message: 'Escribe el informe',
      inputs: [
        {
          name: 'informe',
          type: 'text',
          placeholder: 'Informe'
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
              let adminForm = {
                informe: data.informe
             };
              this.adminService.archive(adminForm, this.issueID).then(value => {
                 loading.dismiss();
                this.presentToast(value.data.mensaje);
                this.navCtrl.navigateRoot('/tabs-admin');
              }).catch(error => {
                if (!error.response) { error.response = 'ERROR'; }
                  loading.dismiss();
                  this.navCtrl.navigateRoot('/tabs-admin');
                this.presentToast('Error al enviar el informe');
            });
          }
        }
      ]
    });
    await alert.present();
  }
}
