/// <reference types="@angular/localize" />

import { bootstrapApplication } from '@angular/platform-browser';
import { AppComponent } from './app/app.component';
import {provideHttpClient} from '@angular/common/http';
import { AppRoutingModule } from './app/app.routes';
import { importProvidersFrom } from '@angular/core';


bootstrapApplication(AppComponent,{
   providers:[
    provideHttpClient(),
    importProvidersFrom(AppRoutingModule),
   ]
})
