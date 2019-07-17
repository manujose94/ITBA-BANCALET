import { FormControl } from '@angular/forms';

export class DateValidator {

    static isValid(control: FormControl): any {
        console.log('control.value', control.value);

        if (control.value) {

            return {
                'exist': false
            };
        }

        if (control.value % 1 !== 0) {
            return {
                'not a whole number': true
            };
        }


        return null;
    }
}
