import { Component, OnInit } from '@angular/core';
import { UsersService } from './../../../services/users.service';
import { ActivatedRoute } from '@angular/router';
import { ItemsService } from 'src/app/services/items.service';
import { AlertController, LoadingController, ToastController, NavController } from '@ionic/angular';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
@Component({
  selector: 'app-edititem',
  templateUrl: './edititem.page.html',
  styleUrls: ['./edititem.page.scss'],
})
export class EdititemPage implements OnInit {
  item;
  itemID;
  tipoID;
  name;
  succesAddItem = false;
  today: string;
  tipos=  this.itemsService.getTipos();
  public onItemForm: FormGroup;
  constructor(private activatedRoute: ActivatedRoute, private itemsService: ItemsService,
    public alertCtrl: AlertController, public loadingCtrl: LoadingController, private usersService: UsersService,
    public toastCtrl: ToastController, private formBuilder: FormBuilder, public navCtrl: NavController) { }
  ngOnInit() {
      const id = this.activatedRoute.snapshot.paramMap.get('id');
      this.itemID = id;
      this.itemsService.getItemsID(id).then(value => {
        this.item = value.data.item;
      }).catch(error => {
      if (!error.response) { error.response = 'ERROR'; }
    });
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
    this.onItemForm = this.formBuilder.group({
      name: ['', Validators.compose([Validators.maxLength(30), Validators.pattern('[a-zA-Z ]*'), Validators.required])],
      description: ['', Validators.compose([Validators.maxLength(30), Validators.pattern('[a-zA-Z ]*'), Validators.required])],
      price:  ['', Validators.compose([Validators.required ])],
      fecha_caducidad: ['', Validators.compose([Validators.required ])],
      tipo: ['', Validators.compose([Validators.required ])]
    });
  }
  public onUpdateItem() {
    this.item = {
       name: this.onItemForm.value.name,
        description: this.onItemForm.value.description,
        price:   this.onItemForm.value.price,
        fecha_caducidad: this.onItemForm.value.fecha_caducidad.substring(0, 10),
        tipo: this.onItemForm.value.tipo,
    };
    this.createItem();
  }
public onChangeTipo(S) {
    this.tipoID = S;
  }
async createItem() {
    const loading = await this.loadingCtrl.create({
      message: 'Actualizando item... ',
      translucent: true,
    });
    await loading.present();
    this.itemsService.update(this.item, this.itemID).then(value => {
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
    const alert = await this.alertCtrl.create({
      header: 'Actualizado',
      subHeader: 'Producto subido con exito',
      message: text,
      buttons: [ {
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
    const toast = await this.toastCtrl.create({
      showCloseButton: true,
      message: text,
      duration: 4000
    });
    toast.present();
  }
}
