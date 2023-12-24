export class InvoiceModel{
    constructor(
        public invoiceid: number,
        public contractnumber: string,
        public phonenumber: string,
        public packagename: string,
        public invoicename: string,
        public num1: number,
        public num2: number,
        public num3: number,
        public period: number
    ){}
}
