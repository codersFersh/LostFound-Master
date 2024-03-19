import http from "@/http";
import { FoundList, FoundPram ,FoundStatus} from "./FoundModel";


//列表
export const foundList = (parm:FoundList)=>{
  return http.get("/found/list",parm)
}

//新增
export const foundAdd = (parm:FoundPram)=>{
  return http.post("/found/add",parm)
}

//获取单条数据
export const foundById = (lfId:string)=>{
  return http.get(`/found/By/${lfId}`)
}

//编辑
export const foundEdit = (parm:FoundPram)=>{
  return http.post("/found/edit",parm)
}

//删除
export const foundDel = (lfId:string)=>{
  return http.delete(`/found/${lfId}`)
}

//管理员删除
export const foundAdminDel = (lfId:string)=>{
  return http.delete(`/found/admin/${lfId}`)
}

//管理员审核状态
export const foundAdminStatus = (parm:FoundStatus)=>{
  return http.post("/found/admin/status",parm)
}

//列表
export const isPassFoundList = (parm:FoundList)=>{
  return http.get("/found/isPassList",parm)
}