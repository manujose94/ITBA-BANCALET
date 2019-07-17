import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { NavController, MenuController, ToastController, AlertController, LoadingController } from '@ionic/angular';
import { SettingsProvider } from 'src/app/providers/settings';
import { AuthService } from 'src/app/services/auth/auth.service';
import { UsersService } from 'src/app/services/users.service';
@Component({
  selector: 'app-login',
  templateUrl: './login.page.html',
  styleUrls: ['./login.page.scss'],
})
export class LoginPage implements OnInit {
  public onLoginForm: FormGroup;
  contactForm;
  contactValid;
  registerCredentials = { username: '', password: '' };
  constructor(
    public alertController: AlertController,
    public navCtrl: NavController,
    public menuCtrl: MenuController,
    public toastCtrl: ToastController,
    public alertCtrl: AlertController,
    public loadingCtrl: LoadingController,
    private formBuilder: FormBuilder,
    private authService: AuthService,
    private usersService: UsersService,
    public settings: SettingsProvider
  ) { }
  ionViewWillEnter() {
    this.menuCtrl.enable(false);
  }
  ngOnInit() {
    this.settings.load().then(() => {
   });
    this.onLoginForm = this.formBuilder.group({
      'username': [null, Validators.compose([
        Validators.required
      ])],
      'password': [null, Validators.compose([
        Validators.required
      ])]
    });
  }
  async forgotPass() {
    let alert = await this.alertCtrl.create({
      header: '¿Necesitas ayuda?',
      message: 'Contacta con nosotros.',
      inputs: [
        {
          name: 'name',
          type: 'text',
          placeholder: 'Nombre'
        },
        {
          name: 'email',
          type: 'email',
          placeholder: 'Email'
        }, {
          name: 'subject',
          type: 'text',
          placeholder: 'Asunto'
        }, {
          name: 'mensaje',
          type: 'text',
          placeholder: 'Mensaje'
        }
      ],
      buttons: [
        {
          text: 'Cancel',
          role: 'cancel',
          cssClass: 'secondary',
        }, {
          text: 'Confirm',
          handler: async (data) => {
            this.contactForm = {
              name: data.name,
              email: data.email,
              subject: data.subject,
              mensaje: data.mensaje,
           };
            const loading = await this.loadingCtrl.create({
              duration: 2000
            });
            loading.present();
            this.authService.contactAdmin(this.contactForm).then(value => {
              this.presentToast(value.data.mensaje);
            }).catch(error => {
              loading.dismiss();
              if (error.toString().includes("403")) {//forbidden no debes estar logeado para crear usuarios
                this.presentToast('Ha surgido un problema: Los usuarios logeados no pueden contactar desde esta página.');
              }
              if (error.toString().includes("400")) {//badrequest el formulario tiene eroores
                this.presentToast('Ha surgido un problema: Faltan datos en el formulario o estan vacíos.');
              }
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
      duration: 10000
    });
    toast.present();
  }
  goToRegister() {
    this.navCtrl.navigateRoot('register');
  }
  goToHome() {
    this.settings.load().then(() => {
      const AuthToken = this.settings.allSettings.AuthToken;
      this.authService.loginDTO(this.onLoginForm.value.username, this.onLoginForm.value.password)
        .then(async (value: { headers: { [x: string]: any; }; }) => {
          await this.authService.signIn( value.headers['x-auth-token']);
          this.usersService.getUserUsername(this.onLoginForm.value.username).then(( response: { data: { user: any; id: any; }; }) => {
            this.usersService.currentUser = response.data.user;
            this.usersService.currentUserId = response.data.id;
            this.settings.storeUserInfo(response.data.user, response.data.id);
            if ( response.data.user.role === 'ADMIN') { this.navCtrl.navigateRoot('/tabs-admin'); } else {
              this.navCtrl.navigateRoot('/menu');
            }
          }).catch(error => {
          this.presentToast('Credenciales incorrectas.');
        });
        }).catch(error => {
          this.presentToast('Credenciales incorrectas.');
      });
   });
  }
  async showError(text) {
    const alert = await this.alertController.create({
      header: 'Alert',
      subHeader: 'Subtitle',
      message: text,
      buttons: ['OK']
    });
    await alert.present();
  }
}
