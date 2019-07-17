import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { map } from 'rxjs/internal/operators/map';
import RestClient from './api/RestClient';
import axios from 'axios';
import { SettingsProvider } from '../providers/settings';
@Injectable({
  providedIn: 'root'
})
export class ItemsService extends RestClient {
  items;
  constructor(private http: HttpClient,  public settings: SettingsProvider) {
    super(null);
    settings.getValue('AuthToken').then( value => {
      this.token = value;
      return value;
     });
  }
  tipos = [
    { name : 'Fruta', id: '1' },
    { name: 'Verdura',  id: '2'  },
    { name : 'Pescado', id: '3' },
    { name : 'Carne', id: '4' }
 ];
  public getTipos() {
    return this.tipos;
  }
  public getProduct(id) {
    return this.instance.get(
      'rest/items'
    )( resolve => {
      this.http.get(this.url).
        pipe(map(
          (response) => response
        )).
        subscribe(
          (data) => {
            {
              let item;
              this.items = data;
              this.items = this.items.items;
              for (const i in  this.items) {
                    if ( this.items[i].itemid == id) {
                      item =  this.items[i];
                    }
                }
              resolve(item);
            }
          }
        );
    });
  }
  getItems() {
    return this.instance.get(
      'rest/items'
    );
  }
  getItemsID(id) {
    return this.instance.get(
      'rest/items/' + id
    );
  }
  create(item) {
      this.instance = axios.create({
        baseURL: `${this.protocol}${this.domain}${this.port}${this.root}`,
        timeout: 60000,
        headers: {
          'Content-Type': 'application/json',
          'X-AUTH-TOKEN': this.token
        }
      });
      return this.instance.post(
        'rest/items/additem',
        item
      );
  }
  update(item, id: any) {
    this.instance = axios.create({
      baseURL: `${this.protocol}${this.domain}${this.port}${this.root}`,
      timeout: 60000,
      headers: {
        'Content-Type': 'application/json',
        'X-AUTH-TOKEN': this.token
      }
    });
    return this.instance.put(
      'rest/items/update/' + id,
      item
    );
}
  getItemsUser() {
    this.instance = axios.create({
      baseURL: `${this.protocol}${this.domain}${this.port}${this.root}`,
      timeout: 60000,
      headers: {
        'Content-Type': 'application/json',
        'X-AUTH-TOKEN': this.token
      }
    });
    return this.instance.get(
      'rest/items/myitems'
    );
  }
  putContactar(idItem: any, mensaje: any) {
    this.instance = axios.create({
      baseURL: `${this.protocol}${this.domain}${this.port}${this.root}`,
      timeout: 60000,
      headers: {
        'Content-Type': 'application/json',
        'X-AUTH-TOKEN': this.token
      }
    });
    return this.instance.put(
      'rest/items/sendto/' + idItem,
      {'mensaje': mensaje}
    );
  }
  putRate(id, formRate){
    this.instance = axios.create({
      baseURL: `${this.protocol}${this.domain}${this.port}${this.root}`,
      timeout: 60000,
      headers: {
        'Content-Type': 'application/json',
        'X-AUTH-TOKEN': this.token
      }
    });
    return this.instance.put(
      'rest/items/rate/' + id ,formRate
    );
  }
  putBaja(idItem: any, idComprador: any) {
    this.instance = axios.create({
      baseURL: `${this.protocol}${this.domain}${this.port}${this.root}`,
      timeout: 60000,
      headers: {
        'Content-Type': 'application/json',
        'X-AUTH-TOKEN': this.token
      }
    });
    const urlSearchParams = new URLSearchParams();
    urlSearchParams.append('comprador', idComprador);
    return this.instance.put(
      'rest/items/baja/' + idItem + '?comprador=' + idComprador,
      urlSearchParams
    );

  }
  delete(idItem: any){
    this.instance = axios.create({
      baseURL: `${this.protocol}${this.domain}${this.port}${this.root}`,
      timeout: 60000,
      headers: {
        'Content-Type': 'application/json',
        'X-AUTH-TOKEN': this.token
      }
    });
    return this.instance.delete(
      'rest/items/delete/'+ idItem 
    );

  }
  getItemsFiltro(name: any, tipo: any, min: any, max: any, itemTipoCaducidad: any, fecha_caducidad: any ) {
    const urlSearchParams = new URLSearchParams('name&tipo&slider&minSlaider&maxSlaider&itemTipoCaducidad&fecha_caducidad');
    urlSearchParams.set('name', name);
    urlSearchParams.set('tipo', tipo);
    urlSearchParams.set('minSlaider', min);
    urlSearchParams.set('maxSlaider', max);
    urlSearchParams.set('itemTipoCaducidad', itemTipoCaducidad );
    urlSearchParams.set('fecha_caducidad', fecha_caducidad);
    let params = urlSearchParams.toString().replace(/=&/g, '&');
    params = params.substring(0, params.length - 1);
    return this.instance.get(
      'rest/items' + '?' + params
    );
  }
}


