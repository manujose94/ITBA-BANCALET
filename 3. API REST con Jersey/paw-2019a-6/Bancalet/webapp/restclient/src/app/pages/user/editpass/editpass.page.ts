import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, FormControl } from '@angular/forms';
import { NavController, MenuController, AlertController, LoadingController, ToastController  } from '@ionic/angular';
import { PasswordValidator } from '../../../validators/password.validator';
import { UsersService } from 'src/app/services/users.service';
import { ActivatedRoute } from '@angular/router';
@Component({
  selector: 'app-editpass',
  templateUrl: './editpass.page.html',
  styleUrls: ['./editpass.page.scss'],
})
export class EditpassPage implements OnInit {
  public onRegisterForm: FormGroup;
  id;
  user: any;
  currencyList;
  registerform;
  constructor(
    private activatedRoute: ActivatedRoute,
    public navCtrl: NavController,
    public menuCtrl: MenuController,
    public loadingCtrl: LoadingController,
    private formBuilder: FormBuilder,
    private usersService: UsersService,
    public alertCtrl: AlertController,
    public toastCtrl: ToastController
  ) {}
  validation_messages = {
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
    ngOnInit() {
      this.id = this.activatedRoute.snapshot.paramMap.get('id');
      this.usersService.getUserId(this.id).then(userdata => {
        this.user = userdata.data.user;
        }).catch(error => {
          if (!error.response) { error.response = 'ERROR'; }
      }),
      this.onRegisterForm = this.formBuilder.group({
        password: new FormControl('', Validators.compose([Validators.minLength(6),Validators.maxLength(40),Validators.required])),
        confirmPassword: new FormControl('', Validators.compose([Validators.minLength(6),Validators.maxLength(40),Validators.required]))}, (formGroup: FormGroup) => { return PasswordValidator.areEqual(formGroup);
      });
    }
    async signUp() {
      this.registerform = {
        password: this.onRegisterForm.value.password,
        repeatPassword: this.onRegisterForm.value.confirmPassword
     };
     const alert = await this.alertCtrl.create({
      header: 'Confirmación de cambio de contraseña',
      message: '¿Estas seguro de que quieres cambiar la contraseña?',
      buttons: [
        {
          text: 'Cancelar',
          role: 'cancel',
          cssClass: 'secondary',
        }, {
          text: 'Confirmar',
          handler: async (data) => {
            const loading = await this.loadingCtrl.create({
              message: 'Cambiando la contraseña... ',
              translucent: true,
            });
            await loading.present();
            this.usersService.updatepass(this.registerform).then(value => {
              this.presentToast('Contraseña cambiada');
              loading.dismiss();
              this.navCtrl.navigateBack('/user/'+ this.id);
            }).catch(error => {
              loading.dismiss();
              if (error.toString().includes("400")) {
                this.presentToast('Ha surgido un problema: Las contraseñas no coinciden.');
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
}
