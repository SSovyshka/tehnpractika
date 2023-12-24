import {Component, OnInit} from '@angular/core';
import {InvoiceSearchModel} from "../../../models/invoice.search.model";
import {SimcardSearchModel} from "../../../models/simcard.search.model";
import {take} from "rxjs";
import {environment} from "../../../../environments/environment";
import {HttpClient} from "@angular/common/http";

@Component({
  selector: 'app-invoice',
  templateUrl: './invoice.component.html'
})
export class InvoiceComponent implements OnInit{

    cols = []
    selectedProducts = []
    invoices: any[] = []
    public readonly apiUrl:string = environment.apiUrl;

    formModel:InvoiceSearchModel = new InvoiceSearchModel();

    constructor(
        private htpp: HttpClient
    ) {
    }
    ngOnInit() {
        this.cols = [
            { field: 'phonenumber', header: 'Номер телефону' },
            { field: 'invoicename', header: 'Назва рахунку' },
            { field: 'num1', header: 'Значення 1' },
            { field: 'num2', header: 'Значення 2' },
            { field: 'num3', header: 'Значення 3' },
        ];
    }

    loadData(item: InvoiceSearchModel): void {
        this.htpp
            .post<any[]>(`${this.apiUrl}app/invoice/search`, item)
            .pipe(take(1))
            .subscribe({
                next: (result) => {
                    console.log('loadData->', result);
                    this.invoices = [];
                    this.invoices = result;

                },
                error: (error) => {

                },
                complete: () => {

                }
            });
    }

    public onClickSearchButton(): void {
        this.loadData(this.formModel);
    }



}
