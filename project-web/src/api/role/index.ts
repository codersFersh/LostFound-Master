import http from "@/http";
import { SysRole,RoleListParm,SaveMenuParm } from "./RoleModel";

//新增角色
export const addApi = (parm:SysRole) =>{
  return http.post("/role/add",parm)
}

//角色列表
export const getListApi = (parm:RoleListParm)=>{
  return http.get("/role/list",parm)
 }

 //编辑角色
 export const editApi = (parm:SysRole)=>{
  return http.post("/role/edit",parm)
}

//删除
export const deleteApi = (roleId:string)=>{
  return http.delete(`/role/${roleId}`)
}

//角色下拉
export const getSelectApi = ()=>{
  return http.get("/role/selectList")
}


//分配菜单保存
export const saveRoleMenuApi = (parm:SaveMenuParm)=>{
  return http.post("/role/saveRoleMenu",parm)
}