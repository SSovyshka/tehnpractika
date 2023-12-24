import {Component, OnInit} from '@angular/core';
import {environment} from "../../../../environments/environment";
import {ActivatedRoute, Router} from "@angular/router";
import {HttpClient} from "@angular/common/http";
import {ConfirmationService, MessageService, SelectItem} from "primeng/api";

import {take} from "rxjs";
import {MessageReturnPojoModel} from "../../../models/message.return.pojo.model";
import {OfficialrankModel} from "../../../models/officialrank.model";
import {GroupsModule} from "./groups.module";
import {GrupaModel} from "../../../models/grupa.model";


@Component({
    selector: 'app-filial',
    templateUrl: './groups.component.html'
})
export class GroupComponent implements OnInit{

    // products = [];

    selectedProducts = [];
    selectedForm: GrupaModel = new GrupaModel();

    changeDialogVisible = false;
    changeDialogNewVisible = false;

    cols: any[] = [];
    groups: GrupaModel[] = [];
    formModel: GrupaModel = new GrupaModel();

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

        this.selectedForm = new GrupaModel();
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
        this.selectedForm = new GrupaModel();
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

    public deleteOfficialrank(rowItem: GrupaModel):void{
        this.http
            .delete<MessageReturnPojoModel>(`${this.apiUrl}app/dictionary/group/${rowItem.grupaid}`)
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

    public addOfficialrank(rowItem: GrupaModel):void{
        this.http
            .post<MessageReturnPojoModel>(`${this.apiUrl}app/dictionary/group`, rowItem)
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
            .put<MessageReturnPojoModel>(`${this.apiUrl}app/dictionary/group`, this.selectedForm)
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

    public getOfficialrank(item: GrupaModel): void{
        this.http
            .get<GrupaModel[]>(`${this.apiUrl}app/dictionary/groups`)
            .pipe(take(1))
            .subscribe({
                next: (result) => {
                    this.groups = [];

                    if(result && result.length > 0){

                        result.forEach(item =>{
                            this.groups.push(item);
                        });

                    }else{
                        this.groups = []
                    }

                }
            });
    }



}

