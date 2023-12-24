import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import {FilialComponent} from "./filial.component";

@NgModule({
    imports: [RouterModule.forChild([
        { path: '', component: FilialComponent }
    ])],
    exports: [RouterModule]
})
export class FilialRoutingModule { }
