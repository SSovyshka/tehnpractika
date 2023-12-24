import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PagesRoutingModule } from './pages-routing.module';
import { SimcardComponent } from './simcard/simcard.component';
import { FilialComponent } from './filial/filial.component';
import { InvoiceComponent } from './invoice/invoice.component';
import {ButtonModule} from "primeng/button";
import {CalendarModule} from "primeng/calendar";
import {DialogModule} from "primeng/dialog";
import {DropdownModule} from "primeng/dropdown";
import {InputNumberModule} from "primeng/inputnumber";
import {InputTextModule} from "primeng/inputtext";
import {InputTextareaModule} from "primeng/inputtextarea";
import {PaginatorModule} from "primeng/paginator";
import {RadioButtonModule} from "primeng/radiobutton";
import {RippleModule} from "primeng/ripple";
import {SharedModule} from "primeng/api";
import {TableModule} from "primeng/table";
import {ToastModule} from "primeng/toast";

@NgModule({
    declarations: [  ],
    imports: [
        CommonModule,
        PagesRoutingModule,
        ButtonModule,
        CalendarModule,
        DialogModule,
        DropdownModule,
        InputNumberModule,
        InputTextModule,
        InputTextareaModule,
        PaginatorModule,
        RadioButtonModule,
        RippleModule,
        SharedModule,
        TableModule,
        ToastModule
    ]
})
export class PagesModule { }
