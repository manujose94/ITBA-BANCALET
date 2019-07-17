import axios from 'axios';
import config from '../../config';

class RestClient {
  protocol;
  domain;
  port;
  instance;
  root;
  url;
  token;
  ERRORS = {
    ERROR_401 : 'Ruta Authorization Required',
    ERROR_404 : 'Ruta Not Found'
  };
  constructor(myToken: any) {
    if (myToken) {
      this.token = myToken;
    }
    this.init();
  }
  init() {
    this.protocol = config.API_PROTOCOL;
    this.domain = config.API_DOMAIN;
    this.port = config.API_PORT;
    this.root = config.API_ROOT;
    this.url = `${this.protocol}${this.domain}${this.port}${this.root}`;
    const headers = {
      'X-AUTH-TOKEN': this.token
    };
    this.instance = axios.create({
      baseURL: `${this.protocol}${this.domain}${this.port}${this.root}`,
      timeout: 60000,
      headers: headers,
    });
    this.instance.interceptors.response.use(
      (response: any) => {
        return response;
      }, (error: any) => {
        if ( error.response.status === 401) {
          return Promise.reject(this.ERRORS.ERROR_401);
        } else if ( error.response.status === 404) {
          return Promise.reject(this.ERRORS.ERROR_401);
        } else {
         return Promise.reject(error.response.statusText);
        }
      });
  }
  public setToken(value: any) {
    this.token = value;
    const headers = {
      'X-AUTH-TOKEN': this.token
    };
    this.instance = axios.create({
      baseURL: `${this.protocol}${this.domain}${this.port}${this.root}`,
      timeout: 60000,
      headers: headers,
    });
  }
}
export default RestClient ;
