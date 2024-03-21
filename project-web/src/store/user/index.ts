import { defineStore } from 'pinia'
import { getInfoApi } from '@/api/user'
//定义store
export const userSotre = defineStore('userSotre', {
    state: () => {
        return {
            userId:'',
            nickName:'',
            sex:'',
            token:'',
            roleId:'',
            codeList: []
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
        },
        getToken(state) {
            return state.token
        },
        getRoleId(state) {
            return state.roleId
        },
        getCodeList(state){
            return state.codeList
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
        },
        setToken(token: string) {
            this.token = token;
        },
        setRoleId(roleId: string) {
            this.roleId = roleId;
        },
        getInfo() {
            return new Promise((resolve, reject) => {
                getInfoApi(this.userId).then((res) => {
                    if (res && res.code == 200) {
                        this.codeList = res.data.permissons
                    }
                    resolve(this.codeList)
                }).catch((error) => {
                    reject(error)
                })
            })
        }
    },
    persist: {
      enabled: true,  //开启持久化操作
      strategies: [
        { storage: sessionStorage, paths: ['userId','nickName','sex','token','roleId'] }
      ]
    }
})