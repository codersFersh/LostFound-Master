import http from "@/http";
import { InfoParm,infoListPram,EditParm,myInfoParm,EditMyInfoPram, AdminInfoParm } from "./infoModel";

//新增
export const AddInfo = (parm:InfoParm)=>{
  return http.post("/info/add",parm)
} 

//列表
export const infoList = (parm:infoListPram)=>{
  return http.get("/info/list",parm)
}
//我的申请列表
export const myinfo = (parm:myInfoParm)=>{
  return http.get("/info/myinfo",parm)
}
//审核申请列表
export const adminInfo = (parm:AdminInfoParm)=>{
  return http.get("/info/adminInfo",parm)
}

//编辑
export const infoEdit = (parm:EditParm)=>{
  return http.post("/info/edit",parm)
} 
//编辑
export const MyInfoEdit = (parm:EditMyInfoPram)=>{
  return http.post("/info/editMyInfo",parm)
} 

//删除
export const myinfoDel = (infoId:string)=>{
  return http.delete(`/info/${infoId}`)
}
//删除
export const AdmininfoDel = (infoId:string)=>{
  return http.delete(`/info/${infoId}`)
}
