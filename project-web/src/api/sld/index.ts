import http from "@/http";
import { Sld,SldListParm } from "./SldModel";

//新增
export const SldAdd = (parm:Sld)=>{
  return http.post("/sld/add",parm)
}

//列表
export const SldList = (parm:SldListParm)=>{
  return http.get("/sld/list",parm)
}

//编辑
export const SldEdit = (parm:Sld)=>{
  return http.put("/sld/edit",parm)
}

//删除
export const SldDel = (userId:string)=>{
  return http.delete(`/sld/${userId}`)
}