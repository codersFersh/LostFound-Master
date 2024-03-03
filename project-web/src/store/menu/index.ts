import {defineStore} from 'pinia'
//定义一个store
export const menuStore = defineStore('menuStore',{
    state:()=>{
        return{
            collapse:false
        }
    },
    getters:{
        getCollapse(state){
            return state.collapse
        }
    },
    actions:{
        setCollapse(collapse:boolean){
            this.collapse = collapse;
        }
    }
})