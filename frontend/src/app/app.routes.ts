import { Routes } from '@angular/router';

import { PrediccionComponent } from './features/prediccion/prediccion.component';

export const routes: Routes = [
  {
    path: '',
    component: PrediccionComponent,
  },
  {
    path: '**',
    redirectTo: '',
  },
];
