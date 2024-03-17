import http from "@/http";

//新增
  export const uploadImg = (file:any)=>{
    const formData = new FormData();
    formData.append('file',file)
    return http.post("/upload/img",formData)
  }

  //删除
export const deleteImg = (filename:string)=>{
  return http.delete(`/upload/del/${filename}`)
}