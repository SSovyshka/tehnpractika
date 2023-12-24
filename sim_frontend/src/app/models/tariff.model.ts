export class TariffModel{
    constructor(
        public tariffid	?: number,
        public price	?: number,
        public simlimit?: number,
        public simlimitcalc?: number,
        public period	?: number,
        public periodbegin?: string,
        public periodend?: string
    ) {
    }
}
