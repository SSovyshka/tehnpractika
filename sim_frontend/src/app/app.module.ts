import { APP_INITIALIZER, NgModule  } from '@angular/core';
import { KeycloakAngularModule, KeycloakService } from 'keycloak-angular';
import { HashLocationStrategy, LocationStrategy } from '@angular/common';
import { AppComponent } from './app.component';
import { AppRoutingModule } from './app-routing.module';
import { AppLayoutModule } from './layout/app.layout.module';
import { NotfoundComponent } from './components/notfound/notfound.component';
import { environment } from 'src/environments/environment';
import { ConfirmationService, MessageService } from 'primeng/api';
import {HttpClientModule} from "@angular/common/http";
import { OfficialranksComponent } from './components/pages/officialranks/officialranks.component';

function initializeKeycloak(keycloak: KeycloakService) {
    return () =>
      keycloak.init({
        config: environment.keycloakConfig,
        initOptions: {
          onLoad: 'login-required',
        }
      });
  }

@NgModule({
    declarations: [
        AppComponent, NotfoundComponent
    ],
    imports: [
        AppRoutingModule,
        AppLayoutModule,
        KeycloakAngularModule,
        HttpClientModule
    ],
    providers: [
        { provide: LocationStrategy, useClass: HashLocationStrategy },
        { provide: APP_INITIALIZER, useFactory: initializeKeycloak, multi: true, deps: [KeycloakService] },
        ConfirmationService,
        MessageService,
    ],
    bootstrap: [AppComponent]
})
export class AppModule { }
