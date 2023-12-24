import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import {GroupComponent} from "./groups.component";



@NgModule({
    imports: [RouterModule.forChild([
        { path: '', component: GroupComponent }
    ])],
    exports: [RouterModule]
})
export class GroupsRoutingModule { }
