import {Component, OnInit} from '@angular/core';
import {environment} from "../../../../environments/environment";
import {ActivatedRoute, Router} from "@angular/router";
import {HttpClient} from "@angular/common/http";
import {ConfirmationService, MessageService, SelectItem} from "primeng/api";
import {FilialModel} from "../../../models/filial.model";
import {take} from "rxjs";
import {MessageReturnPojoModel} from "../../../models/message.return.pojo.model";
import {FormsModule} from "@angular/forms";
import {FilialModule} from "./filial.module";

@Component({
  selector: 'app-filial',
  templateUrl: './filial.component.html'
})
export class FilialComponent implements OnInit{

    // products = [];

    selectedProducts = [];
    selectedForm: FilialModel = new FilialModel();

    changeDialogVisible = false;
    changeDialogNewVisible = false;

    cols: any[] = [];
    filials: FilialModel[] = [];
    formModel: FilialModel = new FilialModel();

    constructor(private router: Router,
                private route: ActivatedRoute,
                private http: HttpClient,
                private messageService: MessageService,
                private confirmationService: ConfirmationService){}

    private apiUrl: string = environment.apiUrl;
    ngOnInit() {
        this.cols = [
            { field: 'filialid', header: 'ID' },
            { field: 'filial', header: 'Філіал' },
        ];
        this.getFilial(this.formModel);
    }

    public onClickChangeData(rowItem): void{

        this.selectedForm = new FilialModel();
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
        this.selectedForm = new FilialModel();
        this.changeDialogVisible = false;
        this.changeDialogNewVisible = false;
    }

    public onClickUpdateFilialButton(): void {
        this.putFilial();
    }

    public onClickAddFilialButton(item): void {
        this.addFilial(item);
    }

    public onClickDeleteFilialButton(rowItem):void{
        this.confirmationService.confirm({
            message: "Ви дійсно бажаете видалити даний філіал?",
            header: "Видалення",
            icon: 'pi pi-info-circle',
            acceptButtonStyleClass:"p-button-danger p-button-text",
            rejectButtonStyleClass:"p-button-text p-button-text",
            accept: () => {
                this.deleteFilial(rowItem)
            },
            reject: () => {
                this.messageService.add({ severity: 'error', summary: 'Rejected', detail: 'You have rejected', life: 3000 });
            }
        })
    }

    public deleteFilial(rowItem: FilialModel):void{
        this.http
            .delete<MessageReturnPojoModel>(`${this.apiUrl}app/dictionary/filial/${rowItem.filialid}`)
            .pipe(take(1))
            .subscribe({
                next: (result) => {
                    this.messageService.add({ severity: 'success', summary: 'Success', detail: result.message });
                    this.getFilial(this.formModel);
                },
                error: (error) => {
                    this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error });
                },
                complete: () => {
                }
            });
    }

    public addFilial(rowItem: FilialModel):void{
        this.http
            .post<MessageReturnPojoModel>(`${this.apiUrl}app/dictionary/filial`, rowItem)
            .pipe(take(1))
            .subscribe({
                next: (result) => {


                    this.messageService.add({ severity: 'success', summary: 'Success', detail: result.message });

                    this.getFilial(this.formModel);
                },
                error: (error) => {
                    this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error });
                },
                complete: () => {
                }
            });
    }

    public putFilial(){
        this.http
            .put<MessageReturnPojoModel>(`${this.apiUrl}app/dictionary/filial`, this.selectedForm)
            .pipe(take(1))
            .subscribe({
                next: (result) => {
                    // this.selectedForm = new FilialModel();

                    // this.filials = [];

                    if(result && result.id >= 0) {
                        this.messageService.add({ severity: 'success', summary: 'Success', detail: result.message });
                    }else {
                        this.messageService.add({ severity: 'warn', summary: 'Warning', detail: result.message });
                    }
                    this.getFilial(this.formModel);
                },
                error: (error) => {
                    this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error });
                },
                complete: () => {
                    this.onHideDialog();
                }
            });
    }

    public getFilial(item: FilialModel): void{
        this.http
            .get<FilialModel[]>(`${this.apiUrl}app/dictionary/filials`)
            .pipe(take(1))
            .subscribe({
                next: (result) => {
                    this.filials = [];

                    if(result && result.length > 0){

                        result.forEach(item =>{
                            this.filials.push(item);
                        });

                    }else{
                        this.filials = []
                    }

                }
            });
    }



}

