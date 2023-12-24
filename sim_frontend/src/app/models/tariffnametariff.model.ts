import {TariffnameModel} from "./tariffname.model";
import {TariffModel} from "./tariff.model";

export class TariffnametariffModel{
    constructor(
        public tariffnameid? :number,
        public tariffplanname?	:string,
        public tariffnametariffid? :number,
        public tariffid? :number,
        public tariffName? : TariffnameModel,
        public tariff? : TariffModel
    ) {
    }
}
