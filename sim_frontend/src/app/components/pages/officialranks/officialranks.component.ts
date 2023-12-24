import {Component, OnInit} from '@angular/core';
import {environment} from "../../../../environments/environment";
import {ActivatedRoute, Router} from "@angular/router";
import {HttpClient} from "@angular/common/http";
import {ConfirmationService, MessageService, SelectItem} from "primeng/api";

import {take} from "rxjs";
import {MessageReturnPojoModel} from "../../../models/message.return.pojo.model";
import {OfficialrankModel} from "../../../models/officialrank.model";


@Component({
    selector: 'app-filial',
    templateUrl: './officialranks.component.html'
})
export class OfficialranksComponent implements OnInit{

    // products = [];

    selectedProducts = [];
    selectedForm: OfficialrankModel = new OfficialrankModel();

    changeDialogVisible = false;
    changeDialogNewVisible = false;

    cols: any[] = [];
    ranks: OfficialrankModel[] = [];
    formModel: OfficialrankModel = new OfficialrankModel();

    constructor(private router: Router,
                private route: ActivatedRoute,
                private http: HttpClient,
                private messageService: MessageService,
                private confirmationService: ConfirmationService){}

    private apiUrl: string = environment.apiUrl;
    ngOnInit() {
        this.cols = [
            { field: 'officialrankid', header: 'ID' },
            { field: 'name', header: 'Філіал' },
        ];
        this.getOfficialrank(this.formModel);
    }

    public onClickChangeData(rowItem): void{

        this.selectedForm = new OfficialrankModel();
        this.selectedForm = rowItem;
        this.onShowDialog();

    }

    public onShowDialog():void {
        this.changeDialogVisible = true;
    }
    public onShowNewDialog():void {
        this.changeDialogNewVisible = true;
    }

    public onHideDialog():void {
        this.selectedForm = new OfficialrankModel();
        this.changeDialogVisible = false;
        this.changeDialogNewVisible = false;
    }

    public onClickUpdateButton(): void {
        this.putOfficialrank();
    }

    public onClickAddButton(item): void {
        this.addOfficialrank(item);
    }

    public onClickDeleteButton(rowItem):void{
        this.confirmationService.confirm({
            message: "Ви дійсно бажаете видалити даний філіал?",
            header: "Видалення",
            icon: 'pi pi-info-circle',
            acceptButtonStyleClass:"p-button-danger p-button-text",
            rejectButtonStyleClass:"p-button-text p-button-text",
            accept: () => {
                this.deleteOfficialrank(rowItem)
            },
            reject: () => {
                this.messageService.add({ severity: 'error', summary: 'Rejected', detail: 'You have rejected', life: 3000 });
            }
        })
    }

    public deleteOfficialrank(rowItem: OfficialrankModel):void{
        this.http
            .delete<MessageReturnPojoModel>(`${this.apiUrl}app/dictionary/officialranks/${rowItem.officialrankid}`)
            .pipe(take(1))
            .subscribe({
                next: (result) => {
                    this.messageService.add({ severity: 'success', summary: 'Success', detail: result.message });
                    this.getOfficialrank(this.formModel);
                },
                error: (error) => {
                    this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error });
                },
                complete: () => {
                }
            });
    }

    public addOfficialrank(rowItem: OfficialrankModel):void{
        this.http
            .post<MessageReturnPojoModel>(`${this.apiUrl}app/dictionary/officialrank`, rowItem)
            .pipe(take(1))
            .subscribe({
                next: (result) => {


                    this.messageService.add({ severity: 'success', summary: 'Success', detail: result.message });

                    this.getOfficialrank(this.formModel);
                },
                error: (error) => {
                    this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error });
                },
                complete: () => {
                }
            });
    }

    public putOfficialrank(){
        this.http
            .put<MessageReturnPojoModel>(`${this.apiUrl}app/dictionary/officialrank`, this.selectedForm)
            .pipe(take(1))
            .subscribe({
                next: (result) => {


                    if(result && result.id >= 0) {
                        this.messageService.add({ severity: 'success', summary: 'Success', detail: result.message });
                    }else {
                        this.messageService.add({ severity: 'warn', summary: 'Warning', detail: result.message });
                    }
                    this.getOfficialrank(this.formModel);
                },
                error: (error) => {
                    this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error });
                },
                complete: () => {
                    this.onHideDialog();
                }
            });
    }

    public getOfficialrank(item: OfficialrankModel): void{
        this.http
            .get<OfficialrankModel[]>(`${this.apiUrl}app/dictionary/officialranks`)
            .pipe(take(1))
            .subscribe({
                next: (result) => {
                    this.ranks = [];

                    if(result && result.length > 0){

                        result.forEach(item =>{
                            this.ranks.push(item);
                        });

                    }else{
                        this.ranks = []
                    }

                }
            });
    }



}

