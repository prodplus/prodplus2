import {
  APP_BASE_HREF,
  CommonModule,
  HashLocationStrategy,
  LocationStrategy,
  registerLocaleData,
} from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import localPt from '@angular/common/locales/pt';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatMenuModule } from '@angular/material/menu';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { RouterModule } from '@angular/router';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import {
  CurrencyMaskConfig,
  CurrencyMaskModule,
  CURRENCY_MASK_CONFIG,
} from 'ng2-currency-mask';
import { NgxMaskModule } from 'ngx-mask';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';

registerLocaleData(localPt);

const brasilCurrency: CurrencyMaskConfig = {
  align: 'right',
  allowNegative: false,
  decimal: ',',
  precision: 2,
  prefix: 'R$ ',
  suffix: '',
  thousands: '.',
};

@NgModule({
  declarations: [AppComponent],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    FontAwesomeModule,
    FormsModule,
    ReactiveFormsModule,
    CommonModule,
    NgxMaskModule.forRoot(),
    HttpClientModule,
    MatMenuModule,
    RouterModule,
    CurrencyMaskModule,
  ],
  providers: [
    { provide: APP_BASE_HREF, useValue: '/' },
    { provide: LocationStrategy, useClass: HashLocationStrategy },
    { provide: CURRENCY_MASK_CONFIG, useValue: brasilCurrency },
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}
