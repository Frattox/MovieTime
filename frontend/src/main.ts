/// <reference types="@angular/localize" />

import { bootstrapApplication } from '@angular/platform-browser';
import { AppComponent } from './app/app.component';
import {provideHttpClient} from '@angular/common/http';
import { AppRoutingModule } from './app/app.routes';
import { importProvidersFrom } from '@angular/core';
import { provideAnimationsAsync } from '@angular/platform-browser/animations/async';


bootstrapApplication(AppComponent,{
   providers:[
    provideHttpClient(),
    importProvidersFrom(AppRoutingModule), provideAnimationsAsync(),
   ]
})
