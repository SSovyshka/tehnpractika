import {NgModule} from "@angular/core";
import {RouterModule} from "@angular/router";
import {SimcardComponent} from "./simcard.component";

@NgModule({
    imports: [RouterModule.forChild([
        { path: '', component: SimcardComponent }
    ])],
    exports: [RouterModule]
})
export class SimcardRoutingModule { }
