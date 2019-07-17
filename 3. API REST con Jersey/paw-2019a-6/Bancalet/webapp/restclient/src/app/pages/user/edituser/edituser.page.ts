import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { NavController, MenuController, AlertController, LoadingController, ToastController  } from '@ionic/angular';
import { UsersService } from 'src/app/services/users.service';
@Component({
  selector: 'app-edituser',
  templateUrl: './edituser.page.html',
  styleUrls: ['./edituser.page.scss'],
})
export class EdituserPage implements OnInit {
  id;
  user: any;
  currencyList;
  registerform;
  public onRegisterForm: FormGroup;
  constructor(
    private activatedRoute: ActivatedRoute, 
    public navCtrl: NavController,
    public menuCtrl: MenuController,
    public loadingCtrl: LoadingController,
    private formBuilder: FormBuilder,
    private usersService: UsersService,
    public alertCtrl: AlertController,
    public toastCtrl: ToastController) { }
    country = '';
    validation_messages = {
      'username': [
          { type: 'required', message: 'Debes introducir un nombre de usuario.' },
          { type: 'minlength', message: 'El nombre de usuario debe tener al menos 6 caracteres.' },
          { type: 'maxlength', message: 'El nombre de usuario no puede superar los 15 caracteres.' },
          { type: 'pattern', message: 'El usuario solamente puede contener números o letras.' },
          { type: 'validUsername', message: 'El usuario ya esta en uso.' }
        ],
        'telf': [
          { type: 'required', message: 'Debes introducir un número de telefono.' },
          { type: 'minlength', message: 'El número de telefono debe tener al menos 3 caracteres.' },
          { type: 'maxlength', message: 'El número de telefono no puede superar los 15 caracteres.' },
          { type: 'pattern', message: 'El número de telefono solamente puede contener números.' }
        ],
        'city': [
          { type: 'required', message: 'Debes introducir un ciudad.' },
          { type: 'minlength', message: 'La ciudad debe tener al menos 3 caracteres.' },
          { type: 'maxlength', message: 'La ciudad no puede superar los 40 caracteres.' },
        ],
        'country': [
          { type: 'required', message: 'Debes introducir un País.' },
          { type: 'minlength', message: 'El país debe tener al menos 3 caracteres.' },
          { type: 'maxlength', message: 'El país no puede superar los 40 caracteres.' },
        ],
        'direccion': [
          { type: 'required', message: 'Debes introducir un dirección.' },
          { type: 'minlength', message: 'La dirección debe tener al menos 3 caracteres.' },
          { type: 'maxlength', message: 'La dirección no puede superar los 150 caracteres.' },
        ],
        'code': [
          { type: 'required', message: 'Debes introducir un código.' },
          { type: 'minlength', message: 'El código debe tener al menos 3 caracteres.' },
          { type: 'maxlength', message: 'El código no puede superar los 40 caracteres.' },
          { type: 'pattern', message: 'El código solamente puede contener números o letras.' },
        ],
      };
  ngOnInit() {
    this.id = this.activatedRoute.snapshot.paramMap.get('id');
    this.usersService.getCountrys().then(
      (response) => {
        this.currencyList = response;
      }
    );
    this.usersService.getUserId(this.id).then(userdata => {
      this.user = userdata.data.user;
      }).catch(error => {
        if (!error.response) { error.response = 'ERROR'; }
    }),
    this.onRegisterForm = this.formBuilder.group({
      telf: ['', Validators.compose([Validators.minLength(3),Validators.maxLength(15),Validators.pattern('[0-9]+'),Validators.required])],
      city: ['', Validators.compose([Validators.minLength(3),Validators.maxLength(40), Validators.required])],
      country: [this.country, Validators.compose([Validators.minLength(3),Validators.maxLength(40),Validators.required])],
      direccion: ['', Validators.compose([Validators.minLength(3),Validators.maxLength(150),Validators.required])],
      code: ['', Validators.compose([Validators.minLength(3),Validators.maxLength(40), Validators.pattern('[a-zA-Z0-9]+'), Validators.required ])]
    });
    }
    async signUp() {
      this.registerform = {
        city: this.onRegisterForm.value.city,
        code: this.onRegisterForm.value.code,
        country: this.country,
        direccion: this.onRegisterForm.value.direccion,
        lat: 0.0,
        lon: 0.0,
        tipo: 0,
        telf: this.onRegisterForm.value.telf
     };
     const alert = await this.alertCtrl.create({
      header: 'Confirmación de actualización',
      message: '¿Estas seguro de que quieres actualizar el usuario con los datos introducidos?',
      buttons: [
        {
          text: 'Cancelar',
          role: 'cancel',
          cssClass: 'secondary',
        }, {
          text: 'Confirmar',
          handler: async (data) => {
            const loading = await this.loadingCtrl.create({
              message: 'Actualizando el usuario... ',
              translucent: true,
            });
            await loading.present();
            this.usersService.update(this.registerform).then(value => {
              this.presentToast('Perfil actualizado');
              loading.dismiss();
              this.navCtrl.navigateBack('/user/'+ this.id);
            }).catch(error => {
              loading.dismiss();
              if (error.toString().includes("403")) {
                this.presentToast('Ha surgido un problema: Los usuarios logeados no pueden crear cuentas.');
              }else{
                error.response = 'ERROR';
                this.presentToast('Error en actualizar usuario: ' + this.user.name);
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
  onChange(country) {
    this.country = country;
  }
}
