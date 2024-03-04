import http from "@/http";
import { MenuType } from "./MenuModel";
//获取上级菜单
export const getParentApi = ()=>{
    return http.get("/menu/getParent")
}
//新增
export const addApi = (parm:MenuType)=>{
    return http.post("/menu/add",parm)
}

//列表
export const getListApi = ()=>{
    return http.get("/menu/list")
}

//编辑
export const editApi = (parm:MenuType)=>{
    return http.put("/menu/edit",parm)
}
//删除
export const deleteApi = (menuId:string)=>{
    return http.delete(`/menu/${menuId}`)
}

