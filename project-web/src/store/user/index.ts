import { defineStore } from 'pinia'
//定义store
export const userSotre = defineStore('userSotre', {
    state: () => {
        return {
            userId:'',
            nickName:'',
            sex:''
        }
    },
    getters: {
        getUserId(state) {
            return state.userId
        },
        getNickName(state) {
            return state.nickName
        },
        getSex(state) {
            return state.sex
        }
    },
    actions: {
        setUserId(userId: string) {
            this.userId = userId;
        },
        setNickName(nickName: string) {
            this.nickName = nickName;
        },
        setSex(sex: string) {
            this.sex = sex;
        }
    },
    persist: {
      enabled: true,  //开启持久化操作
      strategies: [
        { storage: localStorage, paths: ['userId','nickName','sex'] }
      ]
    }
})