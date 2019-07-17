import { Component, OnInit } from '@angular/core';
import { NavController,LoadingController,AlertController, ToastController } from '@ionic/angular';
import { AuthService } from 'src/app/services/auth/auth.service';
import { ActivatedRoute } from '@angular/router';
@Component({
  selector: 'app-confirmregister',
  templateUrl: './confirmregister.page.html',
  styleUrls: ['./confirmregister.page.scss'],
})
export class ConfirmregisterPage implements OnInit {
  constructor(private activatedRoute: ActivatedRoute,public alertCtrl: AlertController,private authService: AuthService,public loadingCtrl: LoadingController,public navCtrl: NavController,public toastCtrl: ToastController) { }
  ngOnInit() {
    const id = this.activatedRoute.snapshot.paramMap.get('id');
    this.authService.confirmregister(id).then(value => {
      this.presentToast('Email verificado correctamente, ahora ya puedes acceder a tu cuenta.');
      this.navCtrl.navigateRoot('/');
    }).catch(error => {
      if (error.toString().includes("Forbidden")) {
        this.navCtrl.navigateRoot('/');
        this.presentToast('Ya has verificado tu email.');
      }
      if (error.toString().includes("Ruta Authorization Required")) {
        this.navCtrl.navigateRoot('/');
        this.presentToast('Usuario no encontrado.');
      }
      if (error.toString().includes("403")) {
        this.navCtrl.navigateRoot('/');
        this.presentToast('Ya has verificado tu email.');
      }
      if (error.toString().includes("404")) {
        this.navCtrl.navigateRoot('/');
        this.presentToast('Usuario no encontrado.');
      }
  });
  }
  goToLogin() {
    this.navCtrl.navigateRoot('/');
  }
  async presentToast(text) {
    const toast = await this.toastCtrl.create({
      showCloseButton: true,
      message: text,
      duration: 10000
    });
    toast.present();
  }
}
