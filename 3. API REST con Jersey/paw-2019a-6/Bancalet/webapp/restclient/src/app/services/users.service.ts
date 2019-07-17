import { Injectable } from '@angular/core';
import { HttpClient} from '@angular/common/http';
import { map } from 'rxjs/operators';
import RestClient from './api/RestClient';
import axios from 'axios';
import { SettingsProvider } from '../providers/settings';
@Injectable({
  providedIn: 'root'
})
export class UsersService extends RestClient {
  url2 = './../assets/data/countrys.json';
  users;
  countrys;
  currentUser;
  currentUserId;
  usertoken;
  constructor(private http: HttpClient,  public settings: SettingsProvider) {
    super(null);
    settings.getValue('AuthToken').then( value => {
      this.token = value;
      return value;
     });
  }
  public  getUserInfo() {
      return this.currentUser;
  }
  public  getUserPrincipal() {
    if (this.currentUser) {
      return this.currentUser;
    } else {
      return new Promise(async resolve => {
      await this.settings.load().then(async () => {
        await this.getUserUsername(this.settings.allSettings.user.username).then(( response: { data: { user: any; id: any; }; }) => {
              this.currentUserId = response.data.id;
              this.settings.storeUserInfo(response.data.user, response.data.id);
              this.currentUser = response.data.user;
            }).catch((error: any) => {
          });
      });
      resolve( this.currentUser);
    });
    }

  }
  public getIdUserInfo() {
    return this.currentUserId;
  }
  public getCountrys() {
    return new Promise(resolve => {
      this.http.get(this.url2).
        pipe(map(
          (response) => response
        )).
        subscribe(
          (data) => {
            {
              this.countrys = data;
              resolve(this.countrys);
            }
          }
        );

    });
  }
  getUserId(id) {
    return this.instance.get(
      'rest/users/' + id
    );
  }
  getUserUsername(username) {
    this.init();
    return this.instance.get(
      'rest/users/login/' + username
    );
  }
  getUsersDTO() {
    return this.instance.get(
      'rest/users'
    );
  }
  getHistorialCompra() {
    this.instance = axios.create({
      baseURL: `${this.protocol}${this.domain}${this.port}${this.root}`,
      timeout: 60000,
      headers: {
        'Content-Type': 'application/json',
        'X-AUTH-TOKEN': this.token
      }
    });
    return this.instance.get(
      'rest/users/miscompras'
    );
  }
  getHistorialVentas() {
    this.instance = axios.create({
      baseURL: `${this.protocol}${this.domain}${this.port}${this.root}`,
      timeout: 60000,
      headers: {
        'Content-Type': 'application/json',
        'X-AUTH-TOKEN': this.token
      }
    });
    return this.instance.get(
      'rest/users/misventas'
    );
  }
getMisSugerencias() {
  this.instance = axios.create({
    baseURL: `${this.protocol}${this.domain}${this.port}${this.root}`,
    timeout: 60000,
    headers: {
      'Content-Type': 'application/json',
      'X-AUTH-TOKEN': this.token
    }
  });
  return this.instance.get(
    '/rest/users/missugerencias'
  );
}
  getMisMensajesEnviados() {
    this.instance = axios.create({
      baseURL: `${this.protocol}${this.domain}${this.port}${this.root}`,
      timeout: 60000,
      headers: {
        'Content-Type': 'application/json',
        'X-AUTH-TOKEN': this.token
      }
    });
    return this.instance.get(
      'rest/users/miscontactose'
    );
  }
  getMisMensajesRecibidos() {
    this.instance = axios.create({
      baseURL: `${this.protocol}${this.domain}${this.port}${this.root}`,
      timeout: 60000,
      headers: {
        'Content-Type': 'application/json',
        'X-AUTH-TOKEN': this.token
      }
    });
    return this.instance.get(
      'rest/users/miscontactosr'
    );
  }
getMaxVendor(id) {
  return this.instance.get(
    'rest/users/getMaxVendor/' + id
  );
}
getMaxBuyer(id) {
  return this.instance.get(
    'rest/users/getMaxBuyer/' + id
  );
}
getMaxRated(id) {
  return this.instance.get(
    'rest/users/getMaxRated/' + id
  );
}
getRate(id) {
  return this.instance.get(
    'rest/users/getMaxRated/' + id
  );
}
contactarAdminlogin(contactForm){
  this.instance = axios.create({
    baseURL: `${this.protocol}${this.domain}${this.port}${this.root}`,
    timeout: 60000,
    headers: {
      'Content-Type': 'application/json','X-AUTH-TOKEN': this.token
    }
  });
  return this.instance.post('rest/users/help', contactForm);
}
updatepass(user) {
  this.instance = axios.create({
    baseURL: `${this.protocol}${this.domain}${this.port}${this.root}`,
    timeout: 60000,
    headers: {
      'Content-Type': 'application/json',
      'X-AUTH-TOKEN': this.token
    }
  });
  return this.instance.put(
    'rest/users/editPass/',
    user
  );
}
update(user) {
  this.instance = axios.create({
    baseURL: `${this.protocol}${this.domain}${this.port}${this.root}`,
    timeout: 60000,
    headers: {
      'Content-Type': 'application/json',
      'X-AUTH-TOKEN': this.token
    }
  });
  return this.instance.put(
    'rest/users/edit/',
    user
  );
}
}
