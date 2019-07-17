import { AuthService } from './../../services/auth/auth.service';
import { Component, OnInit } from '@angular/core';
import { UsersService } from 'src/app/services/users.service';
import { Observable } from 'rxjs';
import { NavController} from '@ionic/angular';
import { Router, RouterEvent } from '@angular/router';
import { SettingsProvider } from 'src/app/providers/settings';
@Component({
  selector: 'app-menu',
  templateUrl: './menu.page.html',
  styleUrls: ['./menu.page.scss'],
})
export class MenuPage implements OnInit {
  currentUser;
  currentUserId;
  selectedPath = '';
  pages = [
    {
      title: 'Anuncios',
      url: '/menu/first',
      icon: 'store'
    },
    {
      title: 'Mis Recomendaciones',
      url: '/menu/misrecomendaciones',
      icon: 'favorite'
    },
    {
      title: 'Mis Enviados',
      url: '/menu/miscontactados',
      icon: 'mail'
    },
    {
      title: 'Mensajes Recibidos',
      url: '/menu/mismensajes',
      icon: 'inbox'
    },
    {
      title: 'Ayuda',
      url: '/menu/ayuda',
      icon: 'live_help'
    }
  ];
  constructor( private usersService: UsersService, private authService: AuthService, public navCtrl: NavController
  , private router: Router, public settings: SettingsProvider) {
    this.router.events.subscribe((event: RouterEvent) => {
      if (event && event.url) {
        this.selectedPath = event.url;
      }
    });
  }
  ngOnInit() {
    this.currentUser = this.usersService.getUserInfo();
    this.currentUserId = this.usersService.getIdUserInfo();
    this.usersService.getUserPrincipal().then(data => {
      this.currentUser = data;
      console.log('currentUser',this.currentUser);
      this.currentUserId = this.usersService.getIdUserInfo();
    });
  }
ionViewWillEnter() {
  }
  public async logout() {
    this.navCtrl.navigateRoot('/');
    await this.settings.remove('AuthToken');
    await this.settings.remove('user');
    this.authService.authState.next(false);
  }
}
