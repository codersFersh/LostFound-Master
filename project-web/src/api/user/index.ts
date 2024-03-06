import http from "@/http";
import { User,UserListParm,Login,AssignParm,UpdateParm } from "./UserModel";
//新增
export const addApi = (parm:User)=>{
    return http.post("/user/add",parm)
}

//列表
export const getListApi = (parm:UserListParm)=>{
    return http.get("/user/list",parm)
}

//根据拥挤id查询角色
export const getRoleListApi = (userId:string)=>{
    return http.get("/user/getRoleList",{userId:userId})
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

//验证码
export const getImgApi = ()=>{
    return http.post("/user/getImage")
}
//登录
export const loginApi = (parm:Login)=>{
    return http.post("/user/login",parm)
}

//查询菜单树
export const getAssignTreeApi = (parm:AssignParm)=>{
    return http.get("/user/getAssingTree",parm)
}

//修改密码
export const updatePasswordApi = (parm:UpdateParm)=>{
    return http.post("/user/updatePassword",parm)
} 

//查询用户菜单
export const getMenuListApi = (userId:string)=>{
    return http.get("/user/getMenuList",{userId:userId})
}

//获取用户信息
export const getInfoApi = (userId:string)=>{
    return http.get("/user/getInfo",{userId:userId})
}

//查询用户菜单
export const loginOutApi = ()=>{
    return http.get("/user/loginOutApi")
}

