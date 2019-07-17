import { Component, OnInit } from '@angular/core';
import { ItemsService } from 'src/app/services/items.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { LoadingController, MenuController, NavController, AlertController, ToastController, Events } from '@ionic/angular';
import { UsersService } from 'src/app/services/users.service';
@Component({
  selector: 'app-additem',
  templateUrl: './additem.page.html',
  styleUrls: ['./additem.page.scss'],
})
export class AdditemPage implements OnInit {
  today: string;
  public onItemForm: FormGroup;
  tipoID;
  item;
  currentUser;
  currentUserId;
  succesAddItem = false;
  constructor(    public navCtrl: NavController,
    public alertController: AlertController,
    public toastController: ToastController,
    public menuCtrl: MenuController,
    public loadingCtrl: LoadingController,
    private formBuilder: FormBuilder,
    private itemsService: ItemsService,
    public events: Events,
    private usersService: UsersService) { }
    tipos =  this.itemsService.getTipos();
  ngOnInit() {
    this.initActualDate();
      this.onItemForm = this.formBuilder.group({
        name: ['', Validators.compose([Validators.maxLength(30), Validators.pattern('[a-zA-Z ]*'), Validators.required])],
        description: ['', Validators.compose([Validators.maxLength(30), Validators.pattern('[a-zA-Z ]*'), Validators.required])],
        price:  ['', Validators.compose([Validators.required ])],
        fecha_caducidad: ['', Validators.compose([Validators.required ])],
        tipo: ['', Validators.compose([Validators.required ])]
      });
      this.currentUser = this.usersService.getUserInfo();
      this.currentUserId = this.usersService.getIdUserInfo();
    }
  initActualDate() {
    const today = new Date();
    const day = new Date().getDate();
   const mounth = today.getMonth() + 1;
   let  dd: string;
   let mm: string;
    const yyyy = today.getFullYear();
    if (day < 10) { dd = '0' + day; } else { dd = day.toString(); }
    if (mounth < 10) {
      mm = '0' + mounth;
    } else { mm = mounth.toString(); }
    this.today = yyyy + '-' + mm + '-' + dd;
  }
  public onChangeTipo(S) {
    this.tipoID = S;
  }
  public onAddItem() {
    this.item = {
       name: this.onItemForm.value.name,
        description: this.onItemForm.value.description,
        price:   this.onItemForm.value.price,
        fecha_caducidad: this.onItemForm.value.fecha_caducidad.substring(0, 10),
        tipo: this.onItemForm.value.tipo,
    };
    this.createItem();
  }
  async createItem() {
    const loading = await this.loadingCtrl.create({
      message: 'Insertando item... ',
      translucent: true,
    });
    await loading.present();
    this.itemsService.create(this.item).then(value => {
      this.succesAddItem = true;
       loading.dismiss();
      this.showMessage(value.data.item.name);
    }).catch(error => {
      if (!error.response) { error.response = 'ERROR'; }
        loading.dismiss();
      this.presentToast('Error en insertar item: ' + this.item.name);
  });
  }
  async showMessage(text) {
    const alert = await this.alertController.create({
      header: 'Insertado',
      subHeader: 'Productos subido con exito',
      message: text,
      buttons: [
        {
          text: 'Cerrar',
          role: 'cancel',
          cssClass: 'secondary',
          handler: () => {

          }
        }, {
          text: 'Menu',
          handler: async () => {
            this.navCtrl.navigateRoot('/menu');
          }
        }
      ]
    });
    await alert.present();
  }
  async presentToast(text) {
    const toast = await this.toastController.create({
      message: text,
      duration: 2000
    });
    toast.present();
  }
}
