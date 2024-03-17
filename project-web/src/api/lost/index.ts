import http from "@/http";
import { LostList,LostPram } from "./LostModel";

//列表
export const lostList = (parm:LostList)=>{
  return http.get("/lost/list",parm)
}

//新增
export const LostAdd = (parm:LostPram)=>{
  return http.post("/lost/add",parm)
}