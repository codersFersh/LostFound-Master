import http from "@/http";
import { User,UserListParm,Login,AssignParm,UpdateParm } from "./UserModel";
//新增
export const addApi = (parm:User)=>{
    return http.post("/admin/add",parm)
}

//列表
export const getListApi = (parm:UserListParm)=>{
    return http.get("/admin/list",parm)
}

//根据拥挤id查询角色
export const getRoleListApi = (userId:string)=>{
    return http.get("/admin/getRoleList",{userId:userId})
}

//编辑
export const editApi = (parm:User)=>{
    return http.put("/admin/edit",parm)
}

//删除
export const deleteApi = (userId:string)=>{
    return http.delete(`/admin/${userId}`)
}

//重置密码
export const resetPasswordApi = (parm:{userId:string})=>{
    return http.post("/admin/resetPassword",parm)
}

//验证码
export const getImgApi = ()=>{
    return http.post("/admin/getImage")
}
//登录
export const loginApi = (parm:Login)=>{
    return http.post("/admin/login",parm)
}

//查询菜单树
export const getAssignTreeApi = (parm:AssignParm)=>{
    return http.get("/admin/getAssingTree",parm)
}

//修改密码
export const updatePasswordApi = (parm:UpdateParm)=>{
    return http.post("/admin/updatePassword",parm)
} 

//查询用户菜单
export const getMenuListApi = (userId:string)=>{
    return http.get("/admin/getMenuList",{userId:userId})
}

//获取用户信息
export const getInfoApi = (userId:string)=>{
    return http.get("/admin/getInfo",{userId:userId})
}

//退出登录
export const loginOutApi = ()=>{
    return http.post("/admin/loginOut")
}

