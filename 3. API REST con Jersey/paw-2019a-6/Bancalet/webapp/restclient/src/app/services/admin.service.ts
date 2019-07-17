import RestClient from './api/RestClient';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { SettingsProvider } from '../providers/settings';
import axios from 'axios';
@Injectable({
    providedIn: 'root'
  })
export class AdminService extends RestClient {
    issues;
    constructor(private http: HttpClient,  public settings: SettingsProvider) {
        super(null);
        settings.getValue('AuthToken').then( value => {
          this.token = value;
          return value;
         });
      }
      tipos = [
        { name : 'Dentro de la aplicación', id: '1' },
        { name: 'Fuera de la aplicación',  id: '2'  }
     ];
     public getTipos() {
      return this.tipos;
    }
    getIssuesFiltro(asunto: any, tipo: any){
      this.instance = axios.create({
        baseURL: `${this.protocol}${this.domain}${this.port}${this.root}`,
        timeout: 60000,
        headers: {
          'Content-Type': 'application/json',
          'X-AUTH-TOKEN': this.token
        }
      });
      const urlSearchParams = new URLSearchParams('tipo&asunto');
      urlSearchParams.set('tipo', tipo);
      urlSearchParams.set('asunto', asunto);
    
      let params = urlSearchParams.toString().replace(/=&/g, '&');
      params = params.substring(0, params.length);
      return this.instance.get(
        'rest/admin/issues' + '?' + params
      );
    }
    getArchivedIssuesFiltro(asunto: any, tipo: any){
      this.instance = axios.create({
        baseURL: `${this.protocol}${this.domain}${this.port}${this.root}`,
        timeout: 60000,
        headers: {
          'Content-Type': 'application/json',
          'X-AUTH-TOKEN': this.token
        }
      });
      const urlSearchParams = new URLSearchParams('tipo&asunto');
      urlSearchParams.set('tipo', tipo);
      urlSearchParams.set('asunto', asunto);
    
      let params = urlSearchParams.toString().replace(/=&/g, '&');
      params = params.substring(0, params.length);
      return this.instance.get(
        'rest/admin/archivedIssues' + '?' + params
      );
    }
    getAllUsersAdminFiltro(userNombre: any, userRole: any){
      this.instance = axios.create({
        baseURL: `${this.protocol}${this.domain}${this.port}${this.root}`,
        timeout: 60000,
        headers: {
          'Content-Type': 'application/json',
          'X-AUTH-TOKEN': this.token
        }
      });
      const urlSearchParams = new URLSearchParams('userRoles&userNombre');
      urlSearchParams.set('userRoles', userRole);
      urlSearchParams.set('userNombre', userNombre);
    
      let params = urlSearchParams.toString().replace(/=&/g, '&');
      params = params.substring(0, params.length);
      return this.instance.get(
        'rest/admin/userList' + '?' + params
      );
    }
    getAllUsersAdmin(){
      this.instance = axios.create({
        baseURL: `${this.protocol}${this.domain}${this.port}${this.root}`,
        timeout: 60000,
        headers: {
          'Content-Type': 'application/json',
          'X-AUTH-TOKEN': this.token
        }
      });
      return this.instance.get(
        'rest/admin/userList'
      );
    }
      getIssues() {
        this.instance = axios.create({
          baseURL: `${this.protocol}${this.domain}${this.port}${this.root}`,
          timeout: 60000,
          headers: {
            'Content-Type': 'application/json',
            'X-AUTH-TOKEN': this.token
          }
        });
        return this.instance.get(
          'rest/admin/issues'
        );
      }
      getArchivedIssues() {
        this.instance = axios.create({
          baseURL: `${this.protocol}${this.domain}${this.port}${this.root}`,
          timeout: 60000,
          headers: {
            'Content-Type': 'application/json',
            'X-AUTH-TOKEN': this.token
          }
        });
        return this.instance.get(
          'rest/admin/archivedIssues'
        );
      }
      getUserList() {
        this.instance = axios.create({
          baseURL: `${this.protocol}${this.domain}${this.port}${this.root}`,
          timeout: 60000,
          headers: {
            'Content-Type': 'application/json',
            'X-AUTH-TOKEN': this.token
          }
        });
        return this.instance.get(
          'rest/admin/userList'
        );
      }
      getIssuesID(id) {
        this.instance = axios.create({
          baseURL: `${this.protocol}${this.domain}${this.port}${this.root}`,
          timeout: 60000,
          headers: {
            'Content-Type': 'application/json',
            'X-AUTH-TOKEN': this.token
          }
        });
        return this.instance.get(
          'rest/admin/issues/' + id
        );
      }
      archive(AdminHelpForm, id) {
        this.instance = axios.create({
          baseURL: `${this.protocol}${this.domain}${this.port}${this.root}`,
          timeout: 60000,
          headers: {
            'Content-Type': 'application/json',
            'X-AUTH-TOKEN': this.token
          }
        });
        return this.instance.put(
          'rest/admin/issues/'+id+'/archive', AdminHelpForm
        );
    
      }
      delete(id){
        this.instance = axios.create({
          baseURL: `${this.protocol}${this.domain}${this.port}${this.root}`,
          timeout: 60000,
          headers: {
            'Content-Type': 'application/json',
            'X-AUTH-TOKEN': this.token
          }
        });
        return this.instance.delete(
          'rest/admin/issues/'+id+'/delete'
        );
    
      }
}