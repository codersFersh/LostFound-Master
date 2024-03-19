import http from "@/http";
import { LostList,LostPram, LostStatus } from "./LostModel";

//列表
export const lostList = (parm:LostList)=>{
  return http.get("/lost/list",parm)
}

//新增
export const LostAdd = (parm:LostPram)=>{
  return http.post("/lost/add",parm)
}

//获取单条数据
export const lostById = (lfId:string)=>{
  return http.get(`/lost/By/${lfId}`)
}

//编辑
export const LostEdit = (parm:LostPram)=>{
  return http.post("/lost/edit",parm)
}

//删除
export const LostDel = (lfId:string)=>{
  return http.delete(`/lost/${lfId}`)
}

//管理员删除
export const LostAdminDel = (lfId:string)=>{
  return http.delete(`/lost/admin/${lfId}`)
}

//管理员审核状态
export const LostAdminStatus = (parm:LostStatus)=>{
  return http.post("/lost/admin/status",parm)
}


//审核列表
export const isPassLostList = (parm:LostList)=>{
  return http.get("/lost/isPassList",parm)
}