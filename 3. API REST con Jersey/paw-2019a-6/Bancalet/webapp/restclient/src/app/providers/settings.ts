import { Storage } from '@ionic/storage';
export class SettingsProvider {
private SETTINGS_KEY = '_settings';
public  settings: any;
  _defaults: any;
  _readyPromise: Promise<any>;
  constructor(public storage: Storage, defaults: any) {
    this._defaults = defaults;
  }
  load() {
    return this.storage.get(this.SETTINGS_KEY).then((value) => {
      if (value) {
        this.settings = value;
        return this._mergeDefaults(this._defaults);
      } else {
        return this.setAll(this._defaults).then((val) => {
          this.settings = val;
        });
      }
    });
  }
  _mergeDefaults(defaults: any) {
    for (let k in defaults) {
      if (!(k in this.settings)) {
        this.settings[k] = defaults[k];
      }
    }
    return this.setAll(this.settings);
  }
  merge(settings: any) {
    for (const k in settings) {
      this.settings[k] = settings[k];
    }
    return this.save();
  }
  setValue(key: string, value: any) {
    this.settings[key] = value;
    return this.storage.set(this.SETTINGS_KEY, this.settings);
  }
  setAll(value: any) {
    return this.storage.set(this.SETTINGS_KEY, value);
  }
  getValue(key: string) {
    return this.storage.get(this.SETTINGS_KEY)
      .then(settings => {
        return settings[key];
      });
  }
  save() {
    return this.setAll(this.settings);
  }
  get allSettings() {
    return this.settings;
  }
    storeUserCredentials(token) {
        window.localStorage.setItem('token', token);
        this.setUserCredentials(token);
      }
      storeUserInfo(user,userID) {
        window.localStorage.setItem('user', user);
        window.localStorage.setItem('userID', userID);
        this.settings['user'] = user;
        this.settings['userID'] = userID;
        this.storage.set(this.SETTINGS_KEY, this.settings);
      }
      setUserCredentials(token) {
        this.settings['AuthToken'] = token;
        this.settings['isLoggedIn'] = true;
        return this.storage.set(this.SETTINGS_KEY, this.settings);
      }
      remove ( key: string) {
        this.storage.remove(key);
      }
}
