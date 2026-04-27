import { Routes } from '@angular/router';
import { Login } from './pages/Login/Login';

export const routes: Routes = [

    {
         path: '',
        component: Login
    },

    {
        path: '**',
        redirectTo: '',
    }
    
];
