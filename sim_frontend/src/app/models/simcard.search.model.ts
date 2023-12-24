import {FilialModel} from "./filial.model";
import {GrupaModel} from "./grupa.model";
import {OfficialrankModel} from "./officialrank.model";

export class SimcardSearchModel{
    constructor(
        public simcardid? :number,
        public phonenumber? :string,
        public grupaid? :number,
        public filialid? :number,
        public periodbegin?: string,
        public periodend?: string,
        public officialrankid? :number,
        public tariffnametarifid? :number,
        public addressid? :number,
        public du? :string,
        public mp? :string,
        public note1? :string,
    ) {}
}
