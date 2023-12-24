import { OnInit } from '@angular/core';
import { Component } from '@angular/core';
import { LayoutService } from './service/app.layout.service';

@Component({
    selector: 'app-menu',
    templateUrl: './app.menu.component.html'
})
export class AppMenuComponent implements OnInit {

    model: any[] = [];

    constructor(public layoutService: LayoutService) { }

    ngOnInit() {
        this.model = [
            {
                label: 'Головна',
                items: [
                    {
                        label: 'Сім-карти',
                        icon: 'pi pi-mobile',
                        routerLink: ['/pages/crud']
                    },
                    {
                        label: 'Рахунок',
                        icon: 'pi pi-dollar',
                        routerLink: ['/pages/invoice']
                    }
                ]
            },
            {
                label: 'Довідники',
                icon: 'pi pi-fw pi-briefcase',
                items: [
                    {
                        label: 'Філії',
                        icon: 'pi pi-building',
                        routerLink: ['/pages/filial']
                    },
                    {
                        label: 'Ранги',
                        icon: 'pi pi-id-card',
                        routerLink: ['/pages/officialrank']
                    },
                    {
                        label: 'Групи',
                        icon: 'pi pi-users',
                        routerLink: ['/pages/group']
                    },
                    // {
                    //     label: 'Not Found',
                    //     icon: 'pi pi-fw pi-exclamation-circle',
                    //     routerLink: ['/notfound']
                    // },
                    // {
                    //     label: 'Empty',
                    //     icon: 'pi pi-fw pi-circle-off',
                    //     routerLink: ['/pages/empty']
                    // },
                ]
            },
        ];
    }
}
