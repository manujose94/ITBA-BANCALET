import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { UsersService } from 'src/app/services/users.service';
import { AlertController, LoadingController } from '@ionic/angular';
import { forkJoin } from 'rxjs';
@Component({
  selector: 'app-user',
  templateUrl: './user.page.html',
  styleUrls: ['./user.page.scss'],
})
export class UserPage implements OnInit {
  user: any;
  isMaxVendedor;
  isMaxRated;
  isMaxBuyer;
  id;
  constructor(private activatedRoute: ActivatedRoute, private usersService: UsersService ,
    public alertCtrl: AlertController, public loadingCtrl: LoadingController) { }
  ngOnInit() {
    this.id = this.activatedRoute.snapshot.paramMap.get('id');
    this.getUserInfo(this.id);
  }
  ionViewWillEnter (){
    this.getUserInfo(this.id);
  }
 async getUserInfo(id) {
  const loading = await this.loadingCtrl.create({
    message: 'Cargando Datos de Perfil...',
    translucent: true,
  });
  await loading.present();
  forkJoin(
      this.usersService.getUserId(id).then(userdata => {
        this.user = userdata.data.user;
        }).catch(error => {
          if (!error.response) { error.response = 'ERROR'; }
      }),
      this.usersService.getMaxBuyer(id).then(isbuyer => {
        this.isMaxBuyer = isbuyer.data.value;
        }).catch(error => {
      if (!error.response) { error.response = 'ERROR'; }
          this.isMaxBuyer = false;
      }),
      this.usersService.getMaxRated(id).then(ismaxrated => {
        this.isMaxRated = ismaxrated.data.value;
        }).catch(error => {
          if (!error.response) { error.response = 'ERROR'; }
          this.isMaxRated = false;
      }),
      this.usersService.getMaxVendor(id).then(ismaxven => {
        this.isMaxVendedor = ismaxven.data.value;
        }).catch(error => {
          if (!error.response) { error.response = 'ERROR'; }
          this.isMaxVendedor = false;
      })
  ).subscribe(([userdata, isbuyer, ismaxven, ismaxrated]) => {
    loading.dismiss();
});
}
}
