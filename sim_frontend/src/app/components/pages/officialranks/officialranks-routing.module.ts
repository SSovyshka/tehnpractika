import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import {OfficialranksComponent} from "./officialranks.component";

@NgModule({
    imports: [RouterModule.forChild([
        { path: '', component: OfficialranksComponent }
    ])],
    exports: [RouterModule]
})
export class OfficialranksRoutingModule { }
