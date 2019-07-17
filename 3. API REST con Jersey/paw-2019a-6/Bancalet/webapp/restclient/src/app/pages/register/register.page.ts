import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, FormControl } from '@angular/forms';
import { NavController, MenuController, AlertController, LoadingController, ToastController  } from '@ionic/angular';
import { AuthService } from 'src/app/services/auth/auth.service';
import { PasswordValidator } from '../../validators/password.validator';
import { UsersService } from 'src/app/services/users.service';
@Component({
  selector: 'app-register',
  templateUrl: './register.page.html',
  styleUrls: ['./register.page.scss'],
})
export class RegisterPage implements OnInit {
  public onRegisterForm: FormGroup;
  currencyList;
  registerform;
  constructor(
    public navCtrl: NavController,
    public menuCtrl: MenuController,
    public loadingCtrl: LoadingController,
    private formBuilder: FormBuilder,
    private authService: AuthService,
    private usersService: UsersService,
    public alertCtrl: AlertController,
    public toastCtrl: ToastController
  ) {}
  avatarId = 0;
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
      'email': [
        { type: 'required', message: 'Debes introducir un email.' },
        { type: 'minlength', message: 'El email debe tener al menos 5 caracteres.' },
        { type: 'maxlength', message: 'El email no puede superar los 40 caracteres.' },
        { type: 'pattern', message: 'No es un email válido.' }
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
      'password': [
        { type: 'required', message: 'La contraseña es obligatoria.' },
        { type: 'minlength', message: 'La contraseña debe tener un tamaño mínimo de 6 caracteres.' },
        { type: 'maxlength', message: 'La contraseña no puede superar los 40 caracteres.' },
      ],
      'confirmPassword': [
        { type: 'required', message: 'Debes confirmar la contraseña.' },
        { type: 'minlength', message: 'La contraseña debe tener un tamaño mínimo de 6 caracteres.' },
        { type: 'maxlength', message: 'La contraseña no puede superar los 40 caracteres.' },
      ]
    };
    avatar_list =  [
      '0',
      '1',
      '2',
      '3',
      '4',
      '5',
      '6'
        ];
  ionViewWillEnter() {
    this.menuCtrl.enable(false);
  }
  ngOnInit() {
    this.usersService.getCountrys().then(
      (response) => {
        this.currencyList = response;
      }
    );
    this.onRegisterForm = this.formBuilder.group({
      numImg: this.avatarId,
      username: ['', Validators.compose([Validators.minLength(6),Validators.maxLength(15),Validators.pattern('[a-zñÑA-Z0-9]+'),Validators.required])],
      telf: ['', Validators.compose([Validators.minLength(3),Validators.maxLength(15),Validators.pattern('[0-9]+'),Validators.required])],
      email: ['',Validators.compose([Validators.minLength(6),Validators.maxLength(40),Validators.pattern('^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+.[a-zA-Z0-9-.]+$'),Validators.required])],
      city: ['', Validators.compose([Validators.minLength(3),Validators.maxLength(40), Validators.required])],
      country: [this.country, Validators.compose([Validators.minLength(3),Validators.maxLength(40),Validators.required])],
      direccion: ['', Validators.compose([Validators.minLength(3),Validators.maxLength(150),Validators.required])],
      code: ['', Validators.compose([Validators.minLength(3),Validators.maxLength(40), Validators.pattern('[a-zA-Z0-9]+'), Validators.required ])],
      password: new FormControl('', Validators.compose([Validators.minLength(6),Validators.maxLength(40),Validators.required])),
      confirmPassword: new FormControl('', Validators.compose([Validators.minLength(6),Validators.maxLength(40),Validators.required]))}, (formGroup: FormGroup) => { return PasswordValidator.areEqual(formGroup);
    });
    }
  goToLogin() {
    this.navCtrl.navigateRoot('/');
  }
  async signUp() {
    this.registerform = {
      username: this.onRegisterForm.value.username,
      password: this.onRegisterForm.value.password,
      repeatPassword: this.onRegisterForm.value.confirmPassword,
      telf: this.onRegisterForm.value.telf,
      email:   this.onRegisterForm.value.email,
      city: this.onRegisterForm.value.city,
      country: this.country,
      code: this.onRegisterForm.value.code,
      direccion: this.onRegisterForm.value.direccion,
      numImg: this.avatarId,
      lat: 0.0,
      lon: 0.0
   };
    const alert = await this.alertCtrl.create({
      header: 'Confirmación de registro',
      message: '¿Estas seguro de que quieres crear un usuario con los datos introducidos?',
      buttons: [
        {
          text: 'Cancelar',
          role: 'cancel',
          cssClass: 'secondary',
        }, {
          text: 'Confirmar',
          handler: async (data) => {
            const loading = await this.loadingCtrl.create({
              message: 'Creando la cuenta... ',
              translucent: true,
            });
            await loading.present();
            this.authService.register(this.registerform).then(value => {
              this.presentToast(value.data.mensaje);
              loading.dismiss();
              this.navCtrl.navigateRoot('/');
            }).catch(error => {
              loading.dismiss();
              if (error.toString().includes("403")) {
                this.presentToast('Ha surgido un problema: Los usuarios logeados no pueden crear cuentas.');
              }
              if (error.toString().includes("400")) {
                this.presentToast('Ha surgido un problema: Las contraseñas no coinciden.');
              }
              if (error.toString().includes("409")) {
                this.presentToast('Ha surgido un problema: El nombre de usuario o email ya existen.');
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
  onChangeAvatar(avatar) {
    this.avatarId = avatar;
  }
}