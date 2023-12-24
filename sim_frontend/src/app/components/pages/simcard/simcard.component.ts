import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {map, Observable, take} from "rxjs";
import {HttpClient} from "@angular/common/http";
import {SimcardSearchModel} from "../../../models/simcard.search.model";
import {SimcardModel} from "../../../models/simcard.model";
import {environment} from "../../../../environments/environment";
import {MessageReturnPojoModel} from "../../../models/message.return.pojo.model";
import {MessageService, SelectItem} from "primeng/api";
import {FilialModel} from "../../../models/filial.model";
import {GrupaModel} from "../../../models/grupa.model";
import {OfficialrankModel} from "../../../models/officialrank.model";

@Component({
  selector: 'app-simcard',
  templateUrl: './simcard.component.html'
})
export class SimcardComponent implements OnInit {

    public loading: boolean = false;

    public phoneNumber: string = '';
    private apiUrl: string = environment.apiUrl;



    filials: SelectItem[] = [];
    groups: SelectItem[] = [];
    officialranks: SelectItem[] = [];

    public cardData: SimcardModel = new SimcardModel();
    constructor(private router: Router,
                private route: ActivatedRoute,
                private http: HttpClient,
                private messageService: MessageService) {
        this.route.params.subscribe(result => {
            this.phoneNumber = result['phonenumber'];
            console.log('this.phoneNumber', this.phoneNumber);
        });
    }

    public ngOnInit() {
        this.getFilials();
        this.getGroups();
        this.getRanks();
        this.loadData({phonenumber: this.phoneNumber});
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

    public loadData(item: SimcardSearchModel): void {
        this.http
            .post<SimcardModel[]>(`${this.apiUrl}app/simcard/search`, item)
            .pipe(take(1))
            .subscribe({
                next: (result) => {
                    console.log('loadData->', result);
                    this.cardData = new SimcardModel();
                    if(result && result.length >0){
                        this.cardData = result[0];
                    }else{
                        this.cardData = new SimcardModel();
                    }
                    // this.cardData = [];
                    // this.cardData = result;

                },
                error: (error) => {
                    this.cardData = new SimcardModel();
                },
                complete: () => {

                }
            });
    }

    public onClickUpdateButton(): void {
        this.updateData(this.cardData);
    }

    public updateData(item: SimcardModel): void {
        this.loading = true;
        this.http
            .put<MessageReturnPojoModel>(`${this.apiUrl}app/simcard/`, item)
            .pipe(take(1))
            .subscribe({
                next: (result) => {
                    this.loading = false;
                    console.log('loadData->', result);
                    if(result && result.id >= 0) {
                        this.messageService.add({ severity: 'success', summary: 'Success', detail: result.message });
                        this.loadData({phonenumber: this.phoneNumber});
                    }else {
                        this.messageService.add({ severity: 'warn', summary: 'Warning', detail: result.message });
                    }
                },
                error: (error) => {
                    this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error });
                    this.loading = false;
                },
                complete: () => {

                }
            });
    }

}
