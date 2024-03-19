import http from "@/http";
import { mylist,MyEditPram } from "./MyModel";

//列表
export const MyList = (parm:mylist)=>{
  return http.get("/my/list",parm)
}

//编辑
export const MyEdit = (parm:MyEditPram)=>{
  return http.post("/my/edit",parm)
}


//删除
export const MyDel = (lfId:string)=>{
  return http.delete(`/my/${lfId}`)
}
