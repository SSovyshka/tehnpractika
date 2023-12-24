import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import {ConfirmationService, MessageService, SelectItem} from 'primeng/api';
import { Table } from 'primeng/table';
import { take } from 'rxjs';
import { environment } from 'src/environments/environment';
import {SimcardSearchModel} from "../../../models/simcard.search.model";
import {Router} from "@angular/router";
import {FilialModel} from "../../../models/filial.model";
import {co} from "@fullcalendar/core/internal-common";
import {GrupaModel} from "../../../models/grupa.model";
import {OfficialrankModel} from "../../../models/officialrank.model";


@Component({
    templateUrl: './crud.component.html',
    providers: [MessageService]
})

export class CrudComponent implements OnInit {

    public readonly apiUrl:string = environment.apiUrl;

    public formModel: SimcardSearchModel = new SimcardSearchModel();

    // public filialModel: FilialModel = new FilialModel();
    // public grupaModel: GrupaModel = new GrupaModel();
    // public officialrankModel: OfficialrankModel = new OfficialrankModel();

    productDialog: boolean = false;

    deleteProductDialog: boolean = false;

    deleteProductsDialog: boolean = false;

    products: any[] = [];

    product: any = {};

    filials: SelectItem[] = [];
    groups: SelectItem[] = [];
    officialranks: SelectItem[] = [];

    selectedProducts: any[] = [];

    submitted: boolean = false;

    cols: any[] = [];

    statuses: any[] = [];

    rowsPerPageOptions = [5, 10, 20];

    constructor(private http: HttpClient,
                private router: Router,
                private confirmationService: ConfirmationService,
                private messageService: MessageService) {}

    ngOnInit() {
        this.products = [];
        this.cols = [
            { field: 'phonenumber', header: 'Phone number' },
            { field: 'periodBegin', header: 'Period begin' },
            { field: 'periodEnd', header: 'Period end' },
            { field: 'du', header: 'Du' },
            { field: 'mp', header: 'Mp' },
            { field: 'note1', header: 'Note' }
        ];

        this.statuses = [
            { label: 'INSTOCK', value: 'instock' },
            { label: 'LOWSTOCK', value: 'lowstock' },
            { label: 'OUTOFSTOCK', value: 'outofstock' }
        ];
        this.getFilials();
        this.getRanks();
        this.getGroups();
    }

    public onClickSearchButton(): void {
        this.loadData(this.formModel);
    }

    loadData(item: SimcardSearchModel): void {
        this.http
        .post<any[]>(`${this.apiUrl}app/simcard/search`, item)
        .pipe(take(1))
        .subscribe({
                    next: (result) => {
                        console.log('loadData->', result);
                        this.products = [];
                        this.products = result;

                    },
                    error: (error) => {

                    },
                    complete: () => {

                    }
        });
    }

    public getFilials(): void{
        this.http
            .get<FilialModel[]>(`${this.apiUrl}app/dictionary/filials`)
            .pipe(take(1))
            .subscribe({
                next: (result) => {
                    console.log(result);
                    this.filials = [];

                    if(result && result.length > 0){

                        result.forEach(item =>{
                            this.filials.push({label: item.filial, value: item.filialid});
                        });

                    }else{
                        this.filials = []
                    }

                }
            });
    }

    public getGroups(): void{
        this.http
            .get<GrupaModel[]>(`${this.apiUrl}app/dictionary/groups`)
            .pipe(take(1))
            .subscribe({
                next: (result) => {

                    this.groups = []

                    if(result && result.length > 0){

                        result.forEach(item => {
                            this.groups.push({label: item.grupa, value: item.grupaid});
                        });

                    }else{
                        this.groups = []
                    }

                }
            });
    }

    public getRanks(): void{
        this.http
            .get<OfficialrankModel[]>(`${this.apiUrl}app/dictionary/officialranks`)
            .pipe(take(1))
            .subscribe({
                next: (result) => {
                    this.officialranks = []

                    if(result && result.length > 0){

                        result.forEach(item => {
                            this.officialranks.push({label: item.name, value: item.officialrankid});
                        });

                    }else{
                        this.officialranks = []
                    }
                }
            });
    }

    openNew() {
        this.product = {};
        this.submitted = false;
        this.productDialog = true;
    }

    deleteSelectedProducts() {
        this.deleteProductsDialog = true;
    }

    editProduct(product: any) {
        this.product = { ...product };
        this.productDialog = true;
    }

    deleteProduct(product: any) {
        this.deleteProductDialog = true;
        this.product = { ...product };
    }

    confirmDeleteSelected() {
        this.deleteProductsDialog = false;
        this.products = this.products.filter(val => !this.selectedProducts.includes(val));
        this.messageService.add({ severity: 'success', summary: 'Successful', detail: 'Products Deleted', life: 3000 });
        this.selectedProducts = [];
    }

    confirmDelete() {
        this.deleteProductDialog = false;
        this.products = this.products.filter(val => val.id !== this.product.id);
        this.messageService.add({ severity: 'success', summary: 'Successful', detail: 'Product Deleted', life: 3000 });
        this.product = {};
    }

    hideDialog() {
        this.productDialog = false;
        this.submitted = false;
    }

    saveProduct() {
        this.submitted = true;

        if (this.product.name?.trim()) {
            if (this.product.id) {
                // @ts-ignore
                this.product.inventoryStatus = this.product.inventoryStatus.value ? this.product.inventoryStatus.value : this.product.inventoryStatus;
                this.products[this.findIndexById(this.product.id)] = this.product;
                this.messageService.add({ severity: 'success', summary: 'Successful', detail: 'Product Updated', life: 3000 });
            } else {
                this.product.id = this.createId();
                this.product.code = this.createId();
                this.product.image = 'product-placeholder.svg';
                // @ts-ignore
                this.product.inventoryStatus = this.product.inventoryStatus ? this.product.inventoryStatus.value : 'INSTOCK';
                this.products.push(this.product);
                this.messageService.add({ severity: 'success', summary: 'Successful', detail: 'Product Created', life: 3000 });
            }

            this.products = [...this.products];
            this.productDialog = false;
            this.product = {};
        }
    }

    findIndexById(id: string): number {
        let index = -1;
        for (let i = 0; i < this.products.length; i++) {
            if (this.products[i].id === id) {
                index = i;
                break;
            }
        }

        return index;
    }

    createId(): string {
        let id = '';
        const chars = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';
        for (let i = 0; i < 5; i++) {
            id += chars.charAt(Math.floor(Math.random() * chars.length));
        }
        return id;
    }

    onGlobalFilter(table: Table, event: Event) {
        table.filterGlobal((event.target as HTMLInputElement).value, 'contains');
    }

    protected readonly console = console;

    public onClickSimCardButton(rowItem: any): void {
        console.log('onClickSimCardButton->', JSON.stringify((rowItem)));
        this.router.navigate([`/pages/simcard/${rowItem.phonenumber}`]).then(()=>{
            console.log(this.router.routerState);
        })
    }


}
