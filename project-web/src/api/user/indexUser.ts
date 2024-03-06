import http from "@/http";
import { User,UserListParm,UpdateParm } from "./UserModel";
//新增
export const addApi = (parm:User)=>{
    return http.post("/user/add",parm)
}

//列表
export const getListApi = (parm:UserListParm)=>{
    return http.get("/user/list",parm)
}


//编辑
export const editApi = (parm:User)=>{
    return http.put("/user/edit",parm)
}

//删除
export const deleteApi = (userId:string)=>{
    return http.delete(`/user/${userId}`)
}

//重置密码
export const resetPasswordApi = (parm:{userId:string})=>{
    return http.post("/user/resetPassword",parm)
}

//修改密码
export const updatePasswordApi = (parm:UpdateParm)=>{
    return http.post("/user/updatePassword",parm)
} 


