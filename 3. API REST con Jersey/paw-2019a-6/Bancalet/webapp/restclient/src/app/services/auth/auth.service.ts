import { UsersService } from './../users.service';
import { Injectable } from '@angular/core';
import RestClient from '../api/RestClient';
import { SettingsProvider } from 'src/app/providers/settings';
import { BehaviorSubject, Observable, of } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { ItemsService } from '../items.service';
import { filter } from 'rxjs/operators';
import axios from 'axios';
const TOKEN_KEY = 'AuthToken';
@Injectable({
  providedIn: 'root'
})
export class AuthService extends RestClient {
  user: Observable<any>;
  public authState  =  new  BehaviorSubject(false);
  constructor(private http: HttpClient,  public settings: SettingsProvider,
    public usersService: UsersService , public itemsService: ItemsService) {
    super(null);
    this.user = this.authState
    .asObservable()
    .pipe(filter(response => response));
  }
  loadUser() {
    this.settings.storage.get(TOKEN_KEY).then(data => {
      if (data) {
        this.authState.next(data);
      } else {
        this.authState.next(false);
      }
    });
  }
  async signIn(token) {
    this.authState.next(true);
    await this.settings.storeUserCredentials(token);
    this.setTokenServices(token);
  }
  loginDTO(username, password) {
    this.token = null;
    const urlSearchParams = new URLSearchParams();
    urlSearchParams.append('username', username);
    urlSearchParams.append('password', password);
    return this.instance.post(
      'rest/login',
      urlSearchParams
    );
  }
  register(registerform){
    this.instance = axios.create({
      baseURL: `${this.protocol}${this.domain}${this.port}${this.root}`,
      timeout: 60000,
      headers: {
        'Content-Type': 'application/json'
      }
    });
    return this.instance.post('rest/register', registerform);
  }
  confirmregister(id){
    return this.instance.put(
      'rest/confirmregister/'+ id
    );
  }
  contactAdmin(contactForm){
    this.instance = axios.create({
      baseURL: `${this.protocol}${this.domain}${this.port}${this.root}`,
      timeout: 60000,
      headers: {
        'Content-Type': 'application/json'
      }
    });
    return this.instance.post('rest/contact', contactForm);
  }
  setTokenServices(token: any) {
   this.usersService.setToken(token);
   this.itemsService.setToken(token);
   this.setToken(token);
  }
}
