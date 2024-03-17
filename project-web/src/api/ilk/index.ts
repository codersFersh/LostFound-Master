import http from "@/http";
import { IlkList, IlkPram } from "./ilkModel";

//新增
export const AddIlk = (parm:IlkPram)=>{
  return http.post("/ilk/add",parm)
}

//列表
export const ListIlk = (parm:IlkList)=>{
  return http.get("/ilk/list",parm)
}
//获取选项列表
export const likAll = ()=>{
  return http.get("/ilk/likAll")
}

//编辑
export const EditIlk = (parm:IlkPram)=>{
  return http.post("/ilk/edit",parm)
}

//删除
export const DelIlk = (ilkId:string)=>{
  return http.delete(`/ilk/${ilkId}`)
}