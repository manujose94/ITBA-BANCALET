import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { NavController, MenuController, AlertController, LoadingController, ToastController  } from '@ionic/angular';
import { UsersService } from 'src/app/services/users.service';
@Component({
  selector: 'app-ayuda',
  templateUrl: './ayuda.page.html',
  styleUrls: ['./ayuda.page.scss'],
})
export class AyudaPage implements OnInit {
  public contactUserForm: FormGroup;
  formulario;
  constructor( 
    public navCtrl: NavController,
    public menuCtrl: MenuController,
    public loadingCtrl: LoadingController,
    private formBuilder: FormBuilder,
    private usersService: UsersService,
    public alertCtrl: AlertController,
    public toastCtrl: ToastController
    ) {}
    validation_messages = {
      'asunto': [
          { type: 'required', message: 'Debes introducir un nombre de usuario.' },
          { type: 'minlength', message: 'El nombre de usuario debe tener al menos 6 caracteres.' },
          { type: 'maxlength', message: 'El nombre de usuario no puede superar los 15 caracteres.' },
          { type: 'pattern', message: 'El usuario solamente puede contener números o letras.' },
          { type: 'validUsername', message: 'El usuario ya esta en uso.' }
        ],
        'mensaje': [
          { type: 'required', message: 'Debes introducir un nombre de usuario.' },
          { type: 'minlength', message: 'El nombre de usuario debe tener al menos 6 caracteres.' },
          { type: 'maxlength', message: 'El nombre de usuario no puede superar los 15 caracteres.' },
          { type: 'pattern', message: 'El usuario solamente puede contener números o letras.' },
          { type: 'validUsername', message: 'El usuario ya esta en uso.' }
        ]
      };
  ngOnInit() {
    this.contactUserForm = this.formBuilder.group({
      asunto: ['', Validators.compose([Validators.minLength(3),Validators.maxLength(40), Validators.required])],
      mensaje: ['', Validators.compose([Validators.minLength(3),Validators.maxLength(500),Validators.required])],
      });
  }
  async contactar(){
    this.formulario = {
      name: "USUARIOLOGUEADO",
      email: "USUARIOLOGUEADO@email",
      subject: this.contactUserForm.value.asunto,
      mensaje: this.contactUserForm.value.mensaje
   };
   const alert = await this.alertCtrl.create({
    header: 'Enviar mensaje',
    message: '¿Estas seguro de que quieres enviar el mensaje?',
    buttons: [
      {
        text: 'Cancelar',
        role: 'cancel',
        cssClass: 'secondary',
      }, {
        text: 'Confirmar',
        handler: async (data) => {
          const loading = await this.loadingCtrl.create({
            message: 'Enviando mensaje... ',
            translucent: true,
          });
          await loading.present();
          this.usersService.contactarAdminlogin(this.formulario).then(value => {
            this.presentToast(value.data.mensaje);
            loading.dismiss();
          }).catch(error => {
            loading.dismiss();
            this.presentToast('Ha surgido un problema');
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
}