import {FilialModel} from "./filial.model";
import {GrupaModel} from "./grupa.model";
import {OfficialrankModel} from "./officialrank.model";
import {ContractModel} from "./contract.model";

export class SimcardModel{
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
        public filial?: FilialModel,
        public grupa?: GrupaModel,
        public officialRank?: OfficialrankModel,
        public contract?: ContractModel
    ) {}
}
