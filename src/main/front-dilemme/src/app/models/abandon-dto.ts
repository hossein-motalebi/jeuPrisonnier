import { TypeStrategie } from './type-strategie'

export  interface AbandonDTO{
    idPlayer : number,
    strategie : TypeStrategie,
    strategieExterne : boolean
}
