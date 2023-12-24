import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
    imports: [RouterModule.forChild([
        { path: 'crud', loadChildren: () => import('./crud/crud.module').then(m => m.CrudModule) },
        { path: 'empty', loadChildren: () => import('./empty/emptydemo.module').then(m => m.EmptyDemoModule) },
        { path: 'simcard/:phonenumber', loadChildren: () => import('./simcard/simcard.module').then(m => m.SimcardModule) },
        { path: 'filial', loadChildren: () => import('./filial/filial.module').then(m => m.FilialModule) },
        { path: 'officialrank', loadChildren: () => import('./officialranks/officialranks.module').then(m => m.OfficialranksModule) },
        { path: 'group', loadChildren: () => import('./groups/groups.module').then(m => m.GroupsModule) },
        { path: 'invoice', loadChildren: () => import('./invoice/invoice.module').then(m => m.InvoiceModule) },
        { path: '**', redirectTo: '/notfound' }
    ])],
    exports: [RouterModule]
})
export class PagesRoutingModule { }
